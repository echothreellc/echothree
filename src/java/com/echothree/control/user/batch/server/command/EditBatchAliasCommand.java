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

import com.echothree.control.user.batch.common.edit.BatchAliasEdit;
import com.echothree.control.user.batch.common.edit.BatchEditFactory;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.common.result.EditBatchAliasResult;
import com.echothree.control.user.batch.common.spec.BatchAliasSpec;
import com.echothree.control.user.batch.server.command.util.BatchAliasUtil;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchAlias;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditBatchAliasCommand
        extends BaseAbstractEditCommand<BatchAliasSpec, BatchAliasEdit, EditBatchAliasResult, BatchAlias, BatchAlias> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditBatchAliasCommand */
    public EditBatchAliasCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(BatchAliasUtil.getInstance().getSecurityRoleGroupNameByBatchTypeSpec(spec), SecurityRoles.Edit.name())
                )))
        )));
    }

    @Override
    public EditBatchAliasResult getResult() {
        return BatchResultFactory.getEditBatchAliasResult();
    }

    @Override
    public BatchAliasEdit getEdit() {
        return BatchEditFactory.getBatchAliasEdit();
    }

    BatchAliasType batchAliasType;
    
    @Override
    public BatchAlias getEntity(EditBatchAliasResult result) {
        var batchControl = Session.getModelController(BatchControl.class);
        BatchAlias batchAlias = null;
        var batchTypeName = spec.getBatchTypeName();
        var batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            var batchName = spec.getBatchName();
            var batch = batchControl.getBatchByName(batchType, batchName);

            if(batch != null) {
                var batchAliasTypeName = spec.getBatchAliasTypeName();

                batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

                if(batchAliasType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        batchAlias = batchControl.getBatchAlias(batch, batchAliasType);
                    } else { // EditMode.UPDATE
                        batchAlias = batchControl.getBatchAliasForUpdate(batch, batchAliasType);
                    }

                    if(batchAlias != null) {
                        result.setBatchAlias(batchControl.getBatchAliasTransfer(getUserVisit(), batchAlias));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownBatchAlias.name(), batchTypeName, batchName, batchAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownBatchAliasTypeName.name(), batchTypeName, batchAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownBatchName.name(), batchTypeName, batchName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchAlias;
    }

    @Override
    public BatchAlias getLockEntity(BatchAlias batchAlias) {
        return batchAlias;
    }

    @Override
    public void fillInResult(EditBatchAliasResult result, BatchAlias batchAlias) {
        var batchControl = Session.getModelController(BatchControl.class);

        result.setBatchAlias(batchControl.getBatchAliasTransfer(getUserVisit(), batchAlias));
    }

    @Override
    public void doLock(BatchAliasEdit edit, BatchAlias batchAlias) {
        edit.setAlias(batchAlias.getAlias());
    }

    @Override
    public void canUpdate(BatchAlias batchAlias) {
        var batchControl = Session.getModelController(BatchControl.class);
        var alias = edit.getAlias();
        var duplicateBatchAlias = batchControl.getBatchAliasByAlias(batchAliasType, alias);

        if(duplicateBatchAlias != null && !batchAlias.equals(duplicateBatchAlias)) {
            var batchAliasTypeDetail = batchAlias.getBatchAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateBatchAlias.name(), batchAliasTypeDetail.getBatchType().getLastDetail().getBatchTypeName(),
                    batchAliasTypeDetail.getBatchAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(BatchAlias batchAlias) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchAliasValue = batchControl.getBatchAliasValue(batchAlias);

        batchAliasValue.setAlias(edit.getAlias());

        batchControl.updateBatchAliasFromValue(batchAliasValue, getPartyPK());
    }

}
