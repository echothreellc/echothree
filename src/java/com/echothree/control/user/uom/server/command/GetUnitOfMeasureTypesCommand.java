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

import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypesForm;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureKindLogic;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetUnitOfMeasureTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<UnitOfMeasureType, GetUnitOfMeasureTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.UnitOfMeasureType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetUnitOfMeasureTypesCommand */
    public GetUnitOfMeasureTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    UnitOfMeasureKind unitOfMeasureKind;

    @Override
    protected void handleForm() {
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();

        unitOfMeasureKind = UnitOfMeasureKindLogic.getInstance().getUnitOfMeasureKindByName(this, unitOfMeasureKindName);
    }

    @Override
    protected Long getTotalEntities() {
        var uomControl = Session.getModelController(UomControl.class);

        return hasExecutionErrors() ? null :
                uomControl.countUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
    }

    @Override
    protected Collection<UnitOfMeasureType> getEntities() {
        Collection<UnitOfMeasureType> entities = null;

        if(!hasExecutionErrors()) {
            var uomControl = Session.getModelController(UomControl.class);

            entities = uomControl.getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<UnitOfMeasureType> entities) {
        var result = UomResultFactory.getGetUnitOfMeasureTypesResult();

        if(entities != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(UnitOfMeasureTypeFactory.class)) {
                result.setUnitOfMeasureTypeCount(getTotalEntities());
            }

            result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind));
            result.setUnitOfMeasureTypes(uomControl.getUnitOfMeasureTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
