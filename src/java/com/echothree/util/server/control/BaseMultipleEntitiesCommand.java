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

package com.echothree.util.server.control;

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import java.util.Collection;
import java.util.List;

public abstract class BaseMultipleEntitiesCommand<BE extends BaseEntity, F extends BaseForm>
        extends BaseSimpleCommand<F>
        implements GraphQlSecurityCommand<F> {
    
    protected BaseMultipleEntitiesCommand(CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        super(commandSecurityDefinition, formFieldDefinitions, allowLimits);
    }
    
    protected abstract Collection<BE> getEntities();
    protected abstract BaseResult getResult(Collection<BE> entities);

    public Collection<BE> getEntitiesForGraphQl(UserVisitPK userVisitPK, F form) {
        Collection<BE> entities = null;

        if(canQueryByGraphQl(userVisitPK, form)) {
            entities = getEntities();
        }
        
        return entities;
    }

    @Override
    public SecurityResult security(UserVisitPK userVisitPK, F form) {
        this.form = form;
        setUserVisitPK(userVisitPK);

        return super.security();
    }

    @Override
    protected final BaseResult execute() {
        var entities = getEntities();
        
        return getResult(entities);
    }
    
}
