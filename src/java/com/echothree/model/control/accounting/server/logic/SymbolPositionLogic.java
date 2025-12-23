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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.common.exception.UnknownSymbolPositionNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SymbolPositionLogic
    extends BaseLogic {

    protected SymbolPositionLogic() {
        super();
    }

    public static SymbolPositionLogic getInstance() {
        return CDI.current().select(SymbolPositionLogic.class).get();
    }

    public SymbolPosition getSymbolPositionByName(final ExecutionErrorAccumulator eea, final String symbolPositionName) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var symbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName);

        if(symbolPosition == null) {
            handleExecutionError(UnknownSymbolPositionNameException.class, eea, ExecutionErrors.UnknownSymbolPositionName.name(), symbolPositionName);
        }

        return symbolPosition;
    }

}
