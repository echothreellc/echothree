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

import com.echothree.control.user.batch.common.form.GetBatchAliasForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.server.command.util.BatchAliasUtil;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchAlias;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetBatchAliasCommand
        extends BaseSingleEntityCommand<BatchAlias, GetBatchAliasForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    BatchControl batchControl;

    @Inject
    BatchLogic batchLogic;

    /** Creates a new instance of GetBatchAliasCommand */
    public GetBatchAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(BatchAliasUtil.getInstance().getSecurityRoleGroupNameByBatchTypeSpec(form), SecurityRoles.Review.name())
                ))
        ));
    }

    @Override
    protected BatchAlias getEntity() {
        var batchTypeName = form.getBatchTypeName();
        var batchType = batchLogic.getBatchTypeByName(this, batchTypeName);
        BatchAlias batchAlias = null;

        if(!hasExecutionErrors()) {
            var batchName = form.getBatchName();
            var batch = batchLogic.getBatchByName(this, batchType, batchName);

            if(!hasExecutionErrors()) {
                var batchAliasTypeName = form.getBatchAliasTypeName();
                var batchAliasType = batchLogic.getBatchAliasTypeByName(this, batchType, batchAliasTypeName);

                if(!hasExecutionErrors()) {
                    batchAlias = batchControl.getBatchAlias(batch, batchAliasType);

                    if(batchAlias == null) {
                        addExecutionError(ExecutionErrors.UnknownBatchAlias.name(),
                                batch.getLastDetail().getBatchName(), batchAliasType.getLastDetail().getBatchAliasTypeName());
                    }
                }
            }
        }

        return batchAlias;
    }

    @Override
    protected BaseResult getResult(BatchAlias batchAlias) {
        var result = BatchResultFactory.getGetBatchAliasResult();

        if(batchAlias != null) {
            result.setBatchAlias(batchControl.getBatchAliasTransfer(getUserVisit(), batchAlias));
        }

        return result;
    }

}
