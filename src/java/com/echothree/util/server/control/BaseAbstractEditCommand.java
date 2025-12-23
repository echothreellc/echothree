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

import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseSpec;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;

public abstract class BaseAbstractEditCommand<S extends BaseSpec, E extends BaseEdit, R extends BaseEditResult<E>, BE extends BaseEntity, LE extends BaseEntity>
        extends BaseEditCommand<S, E> {
    
    protected BaseAbstractEditCommand(CommandSecurityDefinition commandSecurityDefinition, List<FieldDefinition> specFieldDefinitions,
            List<FieldDefinition> editFieldDefinitions) {
        super(commandSecurityDefinition, specFieldDefinitions, editFieldDefinitions);
    }
    
    protected abstract R getResult();
    
    protected abstract E getEdit();
    
    protected EntityPermission editModeToEntityPermission(EditMode editMode) {
        EntityPermission entityPermission;
        
        if(editMode == EditMode.UPDATE) {
            entityPermission = EntityPermission.READ_WRITE;
        } else {
            // EditMode.LOCK & EditMode.ABANDON
            entityPermission = EntityPermission.READ_ONLY;
        }
        
        return entityPermission;
    }
    
    protected abstract BE getEntity(R result);
    
    protected abstract LE getLockEntity(BE baseEntity);
    
    protected abstract void fillInResult(R result, BE baseEntity);
    
    private void fillInResult(R result, BE baseEntity, LE lockEntity) {
        fillInResult(result, baseEntity);
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.UPDATE)) {
            result.setEntityLock(getEntityLockTransfer(lockEntity));
        }
    }
    
    protected abstract void doLock(E edit, BE baseEntity);
    
    // This check is called for both EditMode.LOCK and EditMode.UPDATE.
    protected void canEdit(BE baseEntity) {
        // Nothing.
    }
    
    // This check is called for EditMode.UPDATE, after canEdit(...) is called.
    protected void canUpdate(BE baseEntity) {
        // Nothing.
    }
    
    protected abstract void doUpdate(BE baseEntity);
    
    @Override
    protected BaseResult execute() {
        var result = getResult();
        var baseEntity = getEntity(result);
        
        // getEntity(...) may set both SecurityMessages and ExecutionErrors.
        if(!hasSecurityMessages() && !hasExecutionErrors()) {
            var lockEntity = getLockEntity(baseEntity);

            switch(editMode) {
                case LOCK -> {
                    canEdit(baseEntity);

                    // canEdit(...) may set both SecurityMessages and ExecutionErrors.
                    if(!hasSecurityMessages()) {
                        if(!hasExecutionErrors()) {
                            if(lockEntity(lockEntity)) {
                                edit = getEdit();
                                result.setEdit(edit);
                                doLock(edit, baseEntity);
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                        }

                        fillInResult(result, baseEntity, lockEntity);
                    }
                }
                case ABANDON -> unlockEntity(lockEntity);
                case UPDATE -> {
                    canEdit(baseEntity);

                    // canEdit(...) may set both SecurityMessages and ExecutionErrors.
                    if(!hasSecurityMessages()) {
                        if(!hasExecutionErrors()) {
                            canUpdate(baseEntity);

                            if(!hasExecutionErrors()) {
                                if(lockEntityForUpdate(lockEntity)) {
                                    try {
                                        doUpdate(baseEntity);
                                    } finally {
                                        unlockEntity(lockEntity);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            }
                        }

                        if(hasExecutionErrors()) {
                            fillInResult(result, baseEntity, lockEntity);
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    R errorResult = null;
    
    @Override
    protected void saveResultAfterEditValidatorErrors() {
        errorResult = getResult();

        var baseEntity = getEntity(errorResult);
        var lockEntity = getLockEntity(baseEntity);
        
        fillInResult(errorResult, baseEntity, lockEntity);
    }
    
    @Override
    protected BaseResult getBaseResultAfterErrors() {
        return errorResult;
    }
    
}
