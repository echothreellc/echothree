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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.form.GetBatchAliasTypesForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.factory.BatchAliasTypeFactory;
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
public class GetBatchAliasTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<BatchAliasType, GetBatchAliasTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.BatchAliasType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    BatchControl batchControl;

    @Inject
    BatchLogic batchLogic;

    /** Creates a new instance of GetBatchAliasTypesCommand */
    public GetBatchAliasTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    BatchType batchType;

    @Override
    protected void handleForm() {
        batchType = batchLogic.getBatchTypeByName(this, form.getBatchTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : batchControl.countBatchAliasTypesByBatchType(batchType);
    }

    @Override
    protected Collection<BatchAliasType> getEntities() {
        return hasExecutionErrors() ? null : batchControl.getBatchAliasTypes(batchType);
    }

    @Override
    protected BaseResult getResult(Collection<BatchAliasType> entities) {
        var result = BatchResultFactory.getGetBatchAliasTypesResult();

        if(entities != null) {
            result.setBatchType(batchControl.getBatchTypeTransfer(getUserVisit(), batchType));

            if(session.hasLimit(BatchAliasTypeFactory.class)) {
                result.setBatchAliasTypeCount(getTotalEntities());
            }

            result.setBatchAliasTypes(batchControl.getBatchAliasTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
