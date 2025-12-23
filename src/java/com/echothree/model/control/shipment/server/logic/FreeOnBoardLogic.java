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

package com.echothree.model.control.shipment.server.logic;

import com.echothree.control.user.shipment.common.spec.FreeOnBoardUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.shipment.common.exception.DuplicateFreeOnBoardNameException;
import com.echothree.model.control.shipment.common.exception.UnknownDefaultFreeOnBoardException;
import com.echothree.model.control.shipment.common.exception.UnknownFreeOnBoardNameException;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class FreeOnBoardLogic
        extends BaseLogic {

    protected FreeOnBoardLogic() {
        super();
    }

    public static FreeOnBoardLogic getInstance() {
        return CDI.current().select(FreeOnBoardLogic.class).get();
    }

    public FreeOnBoard createFreeOnBoard(final ExecutionErrorAccumulator eea, final String freeOnBoardName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var freeOnBoard = freeOnBoardControl.getFreeOnBoardByName(freeOnBoardName);

        if(freeOnBoard == null) {
            freeOnBoard = freeOnBoardControl.createFreeOnBoard(freeOnBoardName, isDefault, sortOrder, createdBy);

            if(description != null) {
                freeOnBoardControl.createFreeOnBoardDescription(freeOnBoard, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateFreeOnBoardNameException.class, eea, ExecutionErrors.DuplicateFreeOnBoardName.name(), freeOnBoardName);
        }

        return freeOnBoard;
    }

    public FreeOnBoard getFreeOnBoardByName(final ExecutionErrorAccumulator eea, final String freeOnBoardName,
            final EntityPermission entityPermission) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var freeOnBoard = freeOnBoardControl.getFreeOnBoardByName(freeOnBoardName, entityPermission);

        if(freeOnBoard == null) {
            handleExecutionError(UnknownFreeOnBoardNameException.class, eea, ExecutionErrors.UnknownFreeOnBoardName.name(), freeOnBoardName);
        }

        return freeOnBoard;
    }

    public FreeOnBoard getFreeOnBoardByName(final ExecutionErrorAccumulator eea, final String freeOnBoardName) {
        return getFreeOnBoardByName(eea, freeOnBoardName, EntityPermission.READ_ONLY);
    }

    public FreeOnBoard getFreeOnBoardByNameForUpdate(final ExecutionErrorAccumulator eea, final String freeOnBoardName) {
        return getFreeOnBoardByName(eea, freeOnBoardName, EntityPermission.READ_WRITE);
    }

    public FreeOnBoard getFreeOnBoardByUniversalSpec(final ExecutionErrorAccumulator eea,
            final FreeOnBoardUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        FreeOnBoard freeOnBoard = null;
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var freeOnBoardName = universalSpec.getFreeOnBoardName();
        var parameterCount = (freeOnBoardName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    freeOnBoard = freeOnBoardControl.getDefaultFreeOnBoard(entityPermission);

                    if(freeOnBoard == null) {
                        handleExecutionError(UnknownDefaultFreeOnBoardException.class, eea, ExecutionErrors.UnknownDefaultFreeOnBoard.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(freeOnBoardName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.FreeOnBoard.name());

                    if(!eea.hasExecutionErrors()) {
                        freeOnBoard = freeOnBoardControl.getFreeOnBoardByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    freeOnBoard = getFreeOnBoardByName(eea, freeOnBoardName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return freeOnBoard;
    }

    public FreeOnBoard getFreeOnBoardByUniversalSpec(final ExecutionErrorAccumulator eea,
            final FreeOnBoardUniversalSpec universalSpec, boolean allowDefault) {
        return getFreeOnBoardByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public FreeOnBoard getFreeOnBoardByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final FreeOnBoardUniversalSpec universalSpec, boolean allowDefault) {
        return getFreeOnBoardByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public FreeOnBoard getDefaultFreeOnBoard(final ExecutionErrorAccumulator eea) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        var freeOnBoard = freeOnBoardControl.getDefaultFreeOnBoard();

        if(freeOnBoard == null) {
            handleExecutionError(UnknownDefaultFreeOnBoardException.class, eea, ExecutionErrors.UnknownDefaultFreeOnBoard.name());
        }

        return freeOnBoard;
    }

    public void deleteFreeOnBoard(final ExecutionErrorAccumulator eea, final FreeOnBoard freeOnBoard,
            final BasePK deletedBy) {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);

        freeOnBoardControl.deleteFreeOnBoard(freeOnBoard, deletedBy);
    }
}
