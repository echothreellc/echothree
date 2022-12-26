// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.form.GetSymbolPositionsForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.GetSymbolPositionsResult;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetSymbolPositionsCommand
        extends BaseMultipleEntitiesCommand<SymbolPosition, GetSymbolPositionsForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetSymbolPositionsCommand */
    public GetSymbolPositionsCommand(UserVisitPK userVisitPK, GetSymbolPositionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<SymbolPosition> getEntities() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        return accountingControl.getSymbolPositions();
    }
    
    @Override
    protected BaseResult getTransfers(Collection<SymbolPosition> entities) {
        GetSymbolPositionsResult result = AccountingResultFactory.getGetSymbolPositionsResult();
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        result.setSymbolPositions(accountingControl.getSymbolPositionTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
