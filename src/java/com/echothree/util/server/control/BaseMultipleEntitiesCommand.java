// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.persistence.BaseEntity;
import java.util.Collection;
import java.util.List;

public abstract class BaseMultipleEntitiesCommand<BE extends BaseEntity, F extends BaseForm>
        extends BaseSimpleCommand<F>
        implements GraphQlSecurityCommand {
    
    protected BaseMultipleEntitiesCommand(UserVisitPK userVisitPK, F form, CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        super(userVisitPK, form, commandSecurityDefinition, formFieldDefinitions, allowLimits);
    }
    
    protected abstract Collection<BE> getEntities();
    protected abstract BaseResult getTransfers(Collection<BE> entities);

    public Collection<BE> runForGraphQl() {
        Collection<BE> entities = null;
        SecurityResult securityResult = security();

        if(securityResult == null || !securityResult.getHasMessages()) {
            ValidationResult validationResult = validate();

            if((validationResult == null || !validationResult.getHasErrors())) {
                entities = getEntities();
            }
        }
        
        return entities;
    }

    @Override
    public SecurityResult security() {
        return super.security();
    }

    @Override
    protected BaseResult execute() {
        Collection<BE> entities = getEntities();
        
        return getTransfers(entities);
    }
    
}
