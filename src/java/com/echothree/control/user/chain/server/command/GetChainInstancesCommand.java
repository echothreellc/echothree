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

import com.echothree.control.user.chain.common.form.GetChainInstancesForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.ChainLogic;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.factory.ChainInstanceFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetChainInstancesCommand
        extends BasePaginatedMultipleEntitiesCommand<ChainInstance, GetChainInstancesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ChainControl chainControl;

    @Inject
    ChainLogic chainLogic;

    /** Creates a new instance of GetChainInstancesCommand */
    public GetChainInstancesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    Chain chain;

    @Override
    protected void handleForm() {
        chain = chainLogic.getChainByName(this, form.getChainKindName(), form.getChainTypeName(), form.getChainName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : chainControl.countChainInstancesByChain(chain);
    }

    @Override
    protected Collection<ChainInstance> getEntities() {
        return hasExecutionErrors() ? null : chainControl.getChainInstancesByChain(chain);
    }

    @Override
    protected BaseResult getResult(Collection<ChainInstance> entities) {
        var result = ChainResultFactory.getGetChainInstancesResult();

        if(entities != null) {
            result.setChain(chainControl.getChainTransfer(getUserVisit(), chain));

            if(session.hasLimit(ChainInstanceFactory.class)) {
                result.setChainInstanceCount(getTotalEntities());
            }

            result.setChainInstances(chainControl.getChainInstanceTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
