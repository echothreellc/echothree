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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUseTypesForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetUnitOfMeasureKindUseTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<UnitOfMeasureKindUseType, GetUnitOfMeasureKindUseTypesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetUnitOfMeasureKindUseTypesCommand */
    public GetUnitOfMeasureKindUseTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No additional form fields to handle.
    }

    @Override
    protected Long getTotalEntities() {
        var uomControl = Session.getModelController(UomControl.class);

        return uomControl.countUnitOfMeasureKindUseType();
    }

    @Override
    protected Collection<UnitOfMeasureKindUseType> getEntities() {
        var uomControl = Session.getModelController(UomControl.class);

        return uomControl.getUnitOfMeasureKindUseTypes();
    }

    @Override
    protected BaseResult getResult(Collection<UnitOfMeasureKindUseType> entities) {
        var result = UomResultFactory.getGetUnitOfMeasureKindUseTypesResult();

        if(entities != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(UnitOfMeasureKindUseTypeFactory.class)) {
                result.setUnitOfMeasureKindUseTypeCount(getTotalEntities());
            }

            result.setUnitOfMeasureKindUseTypes(uomControl.getUnitOfMeasureKindUseTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
