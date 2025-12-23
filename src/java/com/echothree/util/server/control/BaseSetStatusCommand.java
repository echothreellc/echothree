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

package com.echothree.util.server.control;

import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.util.common.command.BaseSetStatusResult;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public abstract class BaseSetStatusCommand<F extends BaseForm, R extends BaseSetStatusResult, BE extends BaseEntity, LE extends BaseEntity>
        extends BaseSimpleCommand<F> {
    
    protected BaseSetStatusCommand(CommandSecurityDefinition commandSecurityDefinition, List<FieldDefinition> formFieldDefinitions) {
        super(commandSecurityDefinition, formFieldDefinitions, false);
    }
    
    protected abstract R getResult();
    
    protected abstract BE getEntity();
    
    protected abstract LE getLockEntity(BE baseEntity);
    
    protected abstract void doUpdate(BE baseEntity);
    
    @Override
    protected R execute() {
        var result = getResult();
        var baseEntity = getEntity();
        
        if(!hasExecutionErrors()) {
            var entityLockControl = Session.getModelController(EntityLockControl.class);
            var lockEntity = getLockEntity(baseEntity);
            
            if(entityLockControl.lockEntity(lockEntity, getPartyPK()) != 0) {
                if(entityLockControl.lockEntityForUpdate(lockEntity, getPartyPK())) {
                    try {
                        doUpdate(baseEntity);
                    } finally {
                        entityLockControl.unlockEntity(lockEntity, getPartyPK());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.EntityLockFailed.name());
            }
            
            if(hasExecutionErrors()) {
                result.setEntityLock(entityLockControl.getEntityLockTransfer(getUserVisit(), lockEntity));
            }
        }

        return result;
    }
    
}
