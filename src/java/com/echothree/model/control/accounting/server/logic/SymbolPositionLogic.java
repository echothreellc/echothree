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

import com.echothree.control.user.accounting.common.spec.SymbolPositionDescriptionSpec;
import com.echothree.model.control.accounting.common.exception.UnknownSymbolPositionDescriptionException;
import com.echothree.model.control.accounting.common.exception.UnknownSymbolPositionNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SymbolPositionLogic
    extends BaseLogic {

    @Inject
    AccountingControl accountingControl;

    @Inject
    LanguageLogic languageLogic;

    protected SymbolPositionLogic() {
        super();
    }

    public SymbolPosition getSymbolPositionByName(final ExecutionErrorAccumulator eea, final String symbolPositionName,
            final EntityPermission entityPermission) {
        var symbolPosition = accountingControl.getSymbolPositionByName(symbolPositionName, entityPermission);

        if(symbolPosition == null) {
            handleExecutionError(UnknownSymbolPositionNameException.class, eea, ExecutionErrors.UnknownSymbolPositionName.name(), symbolPositionName);
        }

        return symbolPosition;
    }

    public SymbolPosition getSymbolPositionByName(final ExecutionErrorAccumulator eea, final String symbolPositionName) {
        return getSymbolPositionByName(eea, symbolPositionName, EntityPermission.READ_ONLY);
    }

    public SymbolPositionDescription getSymbolPositionDescriptionBySpec(final ExecutionErrorAccumulator eea,
            final SymbolPositionDescriptionSpec spec, final EntityPermission entityPermission) {
        SymbolPositionDescription symbolPositionDescription = null;
        var symbolPositionName = spec.getSymbolPositionName();
        var symbolPosition = getSymbolPositionByName(eea, symbolPositionName);

        if(!hasExecutionErrors(eea)) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(eea, languageIsoName);

            if(!hasExecutionErrors(eea)) {
                symbolPositionDescription = accountingControl.getSymbolPositionDescription(symbolPosition, language, entityPermission);

                if(symbolPositionDescription == null) {
                    handleExecutionError(UnknownSymbolPositionDescriptionException.class, eea, ExecutionErrors.UnknownSymbolPositionDescription.name(), symbolPositionName, languageIsoName);
                }
            }
        }

        return symbolPositionDescription;
    }

}
