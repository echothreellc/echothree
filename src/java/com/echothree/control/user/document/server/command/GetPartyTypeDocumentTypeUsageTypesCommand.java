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

import com.echothree.control.user.document.common.form.GetPartyTypeDocumentTypeUsageTypesForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentTypeUsageTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.model.data.document.server.factory.PartyTypeDocumentTypeUsageTypeFactory;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetPartyTypeDocumentTypeUsageTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyTypeDocumentTypeUsageType, GetPartyTypeDocumentTypeUsageTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTypeDocumentTypeUsageType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DocumentTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    DocumentControl documentControl;

    @Inject
    PartyControl partyControl;

    @Inject
    PartyTypeLogic partyTypeLogic;

    @Inject
    DocumentTypeUsageTypeLogic documentTypeUsageTypeLogic;

    /** Creates a new instance of GetPartyTypeDocumentTypeUsageTypesCommand */
    public GetPartyTypeDocumentTypeUsageTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private PartyType partyType;
    private DocumentTypeUsageType documentTypeUsageType;

    @Override
    protected void handleForm() {
        var partyTypeName = form.getPartyTypeName();
        var documentTypeUsageTypeName = form.getDocumentTypeUsageTypeName();
        var parameterCount = (partyTypeName == null ? 0 : 1) + (documentTypeUsageTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(partyTypeName != null) {
                partyType = partyTypeLogic.getPartyTypeByName(this, partyTypeName);
            } else {
                documentTypeUsageType = documentTypeUsageTypeLogic.getDocumentTypeUsageTypeByName(this,
                        documentTypeUsageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : partyType == null
                ? documentControl.countPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType)
                : documentControl.countPartyTypeDocumentTypeUsageTypesByPartyType(partyType);
    }

    @Override
    protected Collection<PartyTypeDocumentTypeUsageType> getEntities() {
        return hasExecutionErrors() ? null : partyType == null
                ? documentControl.getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType)
                : documentControl.getPartyTypeDocumentTypeUsageTypesByPartyType(partyType);
    }

    @Override
    protected BaseResult getResult(Collection<PartyTypeDocumentTypeUsageType> entities) {
        var result = DocumentResultFactory.getGetPartyTypeDocumentTypeUsageTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(partyType == null) {
                result.setDocumentTypeUsageType(documentControl.getDocumentTypeUsageTypeTransfer(userVisit,
                        documentTypeUsageType));
            } else {
                result.setPartyType(partyControl.getPartyTypeTransfer(userVisit, partyType));
            }

            if(session.hasLimit(PartyTypeDocumentTypeUsageTypeFactory.class)) {
                result.setPartyTypeDocumentTypeUsageTypeCount(getTotalEntities());
            }

            result.setPartyTypeDocumentTypeUsageTypes(documentControl.getPartyTypeDocumentTypeUsageTypeTransfers(userVisit,
                    entities));
        }

        return result;
    }
    
}
