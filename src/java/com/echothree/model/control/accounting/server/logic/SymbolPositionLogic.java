// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.common.exception.UnknownSymbolPositionNameException;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class SymbolPositionLogic
    extends BaseLogic {
    
    private SymbolPositionLogic() {
        super();
    }
    
    private static class SymbolPositionLogicHolder {
        static SymbolPositionLogic instance = new SymbolPositionLogic();
    }
    
    public static SymbolPositionLogic getInstance() {
        return SymbolPositionLogicHolder.instance;
    }

    public SymbolPosition getSymbolPositionByName(final ExecutionErrorAccumulator eea, final String symbolPositionName) {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        SymbolPosition symbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName);

        if(symbolPosition == null) {
            handleExecutionError(UnknownSymbolPositionNameException.class, eea, ExecutionErrors.UnknownSymbolPositionName.name(), symbolPositionName);
        }

        return symbolPosition;
    }

    public SymbolPosition getSymbolPositionByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        SymbolPosition symbolPosition = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.SymbolPosition.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
            
            symbolPosition = accountingControl.getSymbolPositionByEntityInstance(entityInstance, entityPermission);
        }

        return symbolPosition;
    }
    
    public SymbolPosition getSymbolPositionByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getSymbolPositionByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public SymbolPosition getSymbolPositionByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getSymbolPositionByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }

}
