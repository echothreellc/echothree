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

import com.echothree.control.user.batch.common.form.GetBatchAliasesForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.batch.server.logic.BatchLogic;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchAlias;
import com.echothree.model.data.batch.server.factory.BatchAliasFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetBatchAliasesCommand
        extends BasePaginatedMultipleEntitiesCommand<BatchAlias, GetBatchAliasesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    BatchControl batchControl;

    @Inject
    BatchLogic batchLogic;

    /** Creates a new instance of GetBatchAliasesCommand */
    public GetBatchAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    private Batch batch;

    @Override
    protected void handleForm() {
        batch = batchLogic.getBatchByName(this, form.getBatchTypeName(), form.getBatchName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : batchControl.countBatchAliasesByBatch(batch);
    }

    @Override
    protected Collection<BatchAlias> getEntities() {
        return hasExecutionErrors() ? null : batchControl.getBatchAliasesByBatch(batch);
    }

    @Override
    protected BaseResult getResult(Collection<BatchAlias> entities) {
        var result = BatchResultFactory.getGetBatchAliasesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setBatch(batchControl.getBatchTransfer(userVisit, batch));

            if(session.hasLimit(BatchAliasFactory.class)) {
                result.setBatchAliasCount(getTotalEntities());
            }

            result.setBatchAliases(batchControl.getBatchAliasTransfersByBatch(userVisit, batch));
        }

        return result;
    }

}
