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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.form.GetCommunicationSourcesForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.communication.server.factory.CommunicationSourceFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommunicationSourcesCommand
        extends BasePaginatedMultipleEntitiesCommand<CommunicationSource, GetCommunicationSourcesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommunicationSourceTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    CommunicationControl communicationControl;

    /** Creates a new instance of GetCommunicationSourcesCommand */
    public GetCommunicationSourcesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    CommunicationSourceType communicationSourceType;

    @Override
    protected void handleForm() {
        var communicationSourceTypeName = form.getCommunicationSourceTypeName();

        if(communicationSourceTypeName != null) {
            communicationSourceType = communicationControl.getCommunicationSourceTypeByName(communicationSourceTypeName);

            if(communicationSourceType == null) {
                addExecutionError(ExecutionErrors.UnknownCommunicationSourceTypeName.name(), communicationSourceTypeName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                communicationSourceType == null ? communicationControl.countCommunicationSources() :
                communicationControl.countCommunicationSourcesByCommunicationSourceType(communicationSourceType);
    }

    @Override
    protected Collection<CommunicationSource> getEntities() {
        return hasExecutionErrors() ? null :
                communicationSourceType == null ? communicationControl.getCommunicationSources() :
                communicationControl.getCommunicationSourcesByCommunicationSourceType(communicationSourceType);
    }

    @Override
    protected BaseResult getResult(Collection<CommunicationSource> entities) {
        var result = CommunicationResultFactory.getGetCommunicationSourcesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(communicationSourceType != null) {
                result.setCommunicationSourceType(communicationControl.getCommunicationSourceTypeTransfer(userVisit, communicationSourceType));
            }

            if(session.hasLimit(CommunicationSourceFactory.class)) {
                result.setCommunicationSourceCount(getTotalEntities());
            }

            result.setCommunicationSources(communicationControl.getCommunicationSourceTransfers(userVisit, entities));
        }

        return result;
    }
    
}
