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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.form.GetChainInstancesForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
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

public class GetChainInstancesCommand
        extends BaseSimpleCommand<GetChainInstancesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetChainInstancesCommand */
    public GetChainInstancesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        var result = ChainResultFactory.getGetChainInstancesResult();
        var chainKindName = form.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            var chainTypeName = form.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            
            if(chainType != null) {
                var chainName = form.getChainName();
                var chain = chainControl.getChainByName(chainType, chainName);
                
                if(chain != null) {
                    result.setChain(chainControl.getChainTransfer(getUserVisit(), chain));
                    result.setChainInstances(chainControl.getChainInstanceTransfersByChain(getUserVisit(), chain));
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainName.name(), chainKindName, chainTypeName, chainName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }
        
        return result;
    }
    
}
