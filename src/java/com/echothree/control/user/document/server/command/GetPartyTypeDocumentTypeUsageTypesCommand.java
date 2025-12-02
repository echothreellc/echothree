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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.GetPartyTypeDocumentTypeUsageTypesForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.Dependent;

@Dependent
public class GetPartyTypeDocumentTypeUsageTypesCommand
        extends BaseSimpleCommand<GetPartyTypeDocumentTypeUsageTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTypeDocumentTypeUsageType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyTypeDocumentTypeUsageTypesCommand */
    public GetPartyTypeDocumentTypeUsageTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = DocumentResultFactory.getGetPartyTypeDocumentTypeUsageTypesResult();
        var partyTypeName = form.getPartyTypeName();
        var documentTypeUsageTypeName = form.getDocumentTypeUsageTypeName();
        var parameterCount = (partyTypeName == null ? 0 : 1) + (documentTypeUsageTypeName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var documentControl = Session.getModelController(DocumentControl.class);
            var userVisit = getUserVisit();

            if(partyTypeName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyType = partyControl.getPartyTypeByName(partyTypeName);

                if(partyType != null) {
                    result.setPartyType(partyControl.getPartyTypeTransfer(userVisit, partyType));
                    result.setPartyTypeDocumentTypeUsageTypes(documentControl.getPartyTypeDocumentTypeUsageTypeTransfersByPartyType(userVisit, partyType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
                }
            } else if(documentTypeUsageTypeName != null) {
                var documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);

                if(documentTypeUsageType != null) {
                    result.setDocumentTypeUsageType(documentControl.getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsageType));
                    result.setPartyTypeDocumentTypeUsageTypes(documentControl.getPartyTypeDocumentTypeUsageTypeTransfersByDocumentTypeUsageType(userVisit, documentTypeUsageType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
