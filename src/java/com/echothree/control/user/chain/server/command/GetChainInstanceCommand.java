// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.chain.common.result.GetChainInstanceResult;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetChainInstanceCommand
        extends BaseSimpleCommand<GetChainInstanceForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainInstanceName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetChainInstanceCommand */
    public GetChainInstanceCommand(UserVisitPK userVisitPK, GetChainInstanceForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        GetChainInstanceResult result = ChainResultFactory.getGetChainInstanceResult();
        String chainInstanceName = form.getChainInstanceName();
        ChainInstance chainInstance = chainControl.getChainInstanceByName(chainInstanceName);
        
        if(chainInstance != null) {
            result.setChainInstance(chainControl.getChainInstanceTransfer(getUserVisit(), chainInstance));
        } else {
            addExecutionError(ExecutionErrors.UnknownChainInstanceName.name(), chainInstanceName);
        }
        
        return result;
    }
    
}
