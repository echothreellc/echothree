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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.GetPartyTypeDocumentTypeUsageTypeForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentTypeUsageTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyTypeDocumentTypeUsageTypeCommand
        extends BaseSingleEntityCommand<PartyTypeDocumentTypeUsageType, GetPartyTypeDocumentTypeUsageTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTypeDocumentTypeUsageType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    DocumentControl documentControl;

    @Inject
    PartyTypeLogic partyTypeLogic;

    @Inject
    DocumentTypeUsageTypeLogic documentTypeUsageTypeLogic;

    /** Creates a new instance of GetPartyTypeDocumentTypeUsageTypeCommand */
    public GetPartyTypeDocumentTypeUsageTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected PartyTypeDocumentTypeUsageType getEntity() {
        PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType = null;
        var partyTypeName = form.getPartyTypeName();
        var partyType = partyTypeLogic.getPartyTypeByName(this, partyTypeName);

        if(!hasExecutionErrors()) {
            var documentTypeUsageTypeName = form.getDocumentTypeUsageTypeName();
            var documentTypeUsageType = documentTypeUsageTypeLogic.getDocumentTypeUsageTypeByName(this,
                    documentTypeUsageTypeName);

            if(!hasExecutionErrors()) {
                partyTypeDocumentTypeUsageType = documentControl.getPartyTypeDocumentTypeUsageType(partyType,
                        documentTypeUsageType);

                if(partyTypeDocumentTypeUsageType == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeDocumentTypeUsageType.name(), partyTypeName,
                            documentTypeUsageTypeName);
                }
            }
        }

        return partyTypeDocumentTypeUsageType;
    }

    @Override
    protected BaseResult getResult(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        var result = DocumentResultFactory.getGetPartyTypeDocumentTypeUsageTypeResult();

        if(partyTypeDocumentTypeUsageType != null) {
            result.setPartyTypeDocumentTypeUsageType(documentControl.getPartyTypeDocumentTypeUsageTypeTransfer(
                    getUserVisit(), partyTypeDocumentTypeUsageType));
        }

        return result;
    }
    
}
