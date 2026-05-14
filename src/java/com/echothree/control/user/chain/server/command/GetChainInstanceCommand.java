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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.form.GetChainInstanceForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.ChainInstanceLogic;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetChainInstanceCommand
        extends BaseSimpleCommand<GetChainInstanceForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainInstanceName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ChainControl chainControl;

    @Inject
    ChainInstanceLogic chainInstanceLogic;

    /** Creates a new instance of GetChainInstanceCommand */
    public GetChainInstanceCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ChainResultFactory.getGetChainInstanceResult();
        var chainInstanceName = form.getChainInstanceName();
        var chainInstance = chainInstanceLogic.getChainInstanceByName(this, chainInstanceName);
        
        if(!hasExecutionErrors()) {
            result.setChainInstance(chainControl.getChainInstanceTransfer(getUserVisit(), chainInstance));
        }
        
        return result;
    }
    
}
