// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.batch.common.form.CreateBatchAliasForm;
import com.echothree.control.user.batch.server.command.util.BatchAliasUtil;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchAliasTypeDetail;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateBatchAliasCommand
        extends BaseSimpleCommand<CreateBatchAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateBatchAliasCommand */
    public CreateBatchAliasCommand(UserVisitPK userVisitPK, CreateBatchAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(BatchAliasUtil.getInstance().getSecurityRoleGroupNameByBatchTypeSpec(form), SecurityRoles.Create.name())
                        )))
                ))), FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var batchControl = Session.getModelController(BatchControl.class);
        String batchTypeName = form.getBatchTypeName();
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            String batchName = form.getBatchName();
            Batch batch = batchControl.getBatchByName(batchType, batchName);

            if(batch != null) {
                String batchAliasTypeName = form.getBatchAliasTypeName();
                BatchAliasType batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

                if(batchAliasType != null) {
                    BatchAliasTypeDetail batchAliasTypeDetail = batchAliasType.getLastDetail();
                    String validationPattern = batchAliasTypeDetail.getValidationPattern();
                    String alias = form.getAlias();

                    if(validationPattern != null) {
                        Pattern pattern = Pattern.compile(validationPattern);
                        Matcher m = pattern.matcher(alias);

                        if(!m.matches()) {
                            addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                        }
                    }

                    if(!hasExecutionErrors()) {
                        if(batchControl.getBatchAlias(batch, batchAliasType) == null && batchControl.getBatchAliasByAlias(batchAliasType, alias) == null) {
                            batchControl.createBatchAlias(batch, batchAliasType, alias, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateBatchAlias.name(), batchTypeName, batchName, batchAliasTypeName, alias);
                        }
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

        return null;
    }
    
}
