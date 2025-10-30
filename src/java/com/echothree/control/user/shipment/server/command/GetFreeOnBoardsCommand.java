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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.form.GetFreeOnBoardsForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipment.server.factory.FreeOnBoardFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetFreeOnBoardsCommand
        extends BasePaginatedMultipleEntitiesCommand<FreeOnBoard, GetFreeOnBoardsForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetFreeOnBoardsCommand */
    public GetFreeOnBoardsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);

        return freeOnBoardControl.countFreeOnBoards();
    }

    @Override
    protected Collection<FreeOnBoard> getEntities() {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);

        return freeOnBoardControl.getFreeOnBoards();
    }

    @Override
    protected BaseResult getResult(Collection<FreeOnBoard> entities) {
        var result = ShipmentResultFactory.getGetFreeOnBoardsResult();

        if(entities != null) {
            var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);

            if(session.hasLimit(FreeOnBoardFactory.class)) {
                result.setFreeOnBoardCount(getTotalEntities());
            }

            result.setFreeOnBoards(freeOnBoardControl.getFreeOnBoardTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
