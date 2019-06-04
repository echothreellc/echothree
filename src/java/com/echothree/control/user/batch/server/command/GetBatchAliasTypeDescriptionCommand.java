// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.batch.common.form.GetBatchAliasTypeDescriptionForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.common.result.GetBatchAliasTypeDescriptionResult;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchAliasTypeDescription;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.party.server.entity.Language;
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

public class GetBatchAliasTypeDescriptionCommand
        extends BaseSimpleCommand<GetBatchAliasTypeDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.BatchAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetBatchAliasTypeDescriptionCommand */
    public GetBatchAliasTypeDescriptionCommand(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        GetBatchAliasTypeDescriptionResult result = BatchResultFactory.getGetBatchAliasTypeDescriptionResult();
        String batchTypeName = form.getBatchTypeName();
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            String batchAliasTypeName = form.getBatchAliasTypeName();
            BatchAliasType batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

            if(batchAliasType != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    BatchAliasTypeDescription batchAliasTypeDescription = batchControl.getBatchAliasTypeDescription(batchAliasType, language);

                    if(batchAliasTypeDescription != null) {
                        result.setBatchAliasTypeDescription(batchControl.getBatchAliasTypeDescriptionTransfer(getUserVisit(), batchAliasTypeDescription));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownBatchAliasTypeDescription.name(), batchTypeName, batchAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownBatchAliasTypeName.name(), batchTypeName, batchAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }
        
        return result;
    }
    
}
