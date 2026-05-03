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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetEventGroupsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.server.entity.EventGroup;
import com.echothree.model.data.core.server.factory.EventGroupFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEventGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<EventGroup, GetEventGroupsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    EventControl eventControl;
    
    /** Creates a new instance of GetEventGroupsCommand */
    public GetEventGroupsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return eventControl.countEventGroups();
    }

    @Override
    protected Collection<EventGroup> getEntities() {
        return eventControl.getEventGroups();
    }

    @Override
    protected BaseResult getResult(Collection<EventGroup> entities) {
        var result = CoreResultFactory.getGetEventGroupsResult();

        if(entities != null) {
            if(session.hasLimit(EventGroupFactory.class)) {
                result.setEventGroupCount(getTotalEntities());
            }

            result.setEventGroups(eventControl.getEventGroupTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
