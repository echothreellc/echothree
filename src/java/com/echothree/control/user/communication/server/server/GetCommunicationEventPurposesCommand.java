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

import com.echothree.control.user.communication.common.form.GetCommunicationEventPurposesForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommunicationEventPurposesCommand
        extends BasePaginatedMultipleEntitiesCommand<CommunicationEventPurpose, GetCommunicationEventPurposesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    @Inject
    CommunicationControl communicationControl;
    
    /** Creates a new instance of GetCommunicationEventPurposesCommand */
    public GetCommunicationEventPurposesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return communicationControl.countCommunicationEventPurposes();
    }

    @Override
    protected Collection<CommunicationEventPurpose> getEntities() {
        return communicationControl.getCommunicationEventPurposes();
    }

    @Override
    protected BaseResult getResult(Collection<CommunicationEventPurpose> entities) {
        var result = CommunicationResultFactory.getGetCommunicationEventPurposesResult();

        if(entities != null) {
            if(session.hasLimit(CommunicationEventPurposeFactory.class)) {
                result.setCommunicationEventPurposeCount(getTotalEntities());
            }

            result.setCommunicationEventPurposes(communicationControl.getCommunicationEventPurposeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
