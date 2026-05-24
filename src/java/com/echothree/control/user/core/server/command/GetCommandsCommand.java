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

import com.echothree.control.user.core.common.form.GetCommandsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.core.server.logic.ComponentVendorLogic;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.factory.CommandFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommandsCommand
        extends BasePaginatedMultipleEntitiesCommand<Command, GetCommandsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ComponentVendorLogic componentVendorLogic;

    /** Creates a new instance of GetCommandsCommand */
    public GetCommandsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    ComponentVendor componentVendor;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();

        if(componentVendorName != null) {
            componentVendor = componentVendorLogic.getComponentVendorByName(this, componentVendorName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                componentVendor == null ?
                        commandControl.countCommands() :
                        commandControl.countCommandsByComponentVendor(componentVendor);
    }

    @Override
    protected Collection<Command> getEntities() {
        Collection<Command> entities = null;

        if(!hasExecutionErrors()) {
            entities = componentVendor == null ?
                    commandControl.getCommands() :
                    commandControl.getCommandsByComponentVendor(componentVendor);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<Command> entities) {
        var result = CoreResultFactory.getGetCommandsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(componentVendor != null) {
                result.setComponentVendor(componentControl.getComponentVendorTransfer(userVisit, componentVendor));
            }

            if(session.hasLimit(CommandFactory.class)) {
                result.setCommandCount(getTotalEntities());
            }

            result.setCommands(commandControl.getCommandTransfers(userVisit, entities));
        }

        return result;
    }

}
