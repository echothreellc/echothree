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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.GetSymbolPositionForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.SymbolPositionLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetSymbolPositionCommand
        extends BaseSingleEntityCommand<SymbolPosition, GetSymbolPositionForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SymbolPositionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetSymbolPositionCommand */
    public GetSymbolPositionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected SymbolPosition getEntity() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        SymbolPosition symbolPosition = null;
        var symbolPositionName = form.getSymbolPositionName();
        var parameterCount = (symbolPositionName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        switch(parameterCount) {
            case 0:
                symbolPosition = accountingControl.getDefaultSymbolPosition();
                break;
            case 1:
                if(symbolPositionName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.SymbolPosition.name());
                    
                    if(!hasExecutionErrors()) {
                        symbolPosition = accountingControl.getSymbolPositionByEntityInstance(entityInstance);
                    }
                } else {
                    symbolPosition = SymbolPositionLogic.getInstance().getSymbolPositionByName(this, symbolPositionName);
                }
                break;
            default:
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                break;
        }
        
        if(symbolPosition != null) {
            sendEvent(symbolPosition.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return symbolPosition;
    }
    
    @Override
    protected BaseResult getResult(SymbolPosition symbolPosition) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var result = AccountingResultFactory.getGetSymbolPositionResult();

        if(symbolPosition != null) {
            result.setSymbolPosition(accountingControl.getSymbolPositionTransfer(getUserVisit(), symbolPosition));
        }

        return result;
    }
    
}
