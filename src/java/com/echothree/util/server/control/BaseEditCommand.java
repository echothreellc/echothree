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

import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.exception.EntityLockException;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseEditForm;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.BaseSpec;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.BaseValue;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.Validator;
import java.util.List;
import java.util.concurrent.Future;
import javax.inject.Inject;

public abstract class BaseEditCommand<S extends BaseSpec, E extends BaseEdit, R extends BaseEditResult<E>, BE extends BaseEntity, LE extends BaseEntity>
        extends BaseCommand {

    @Inject
    EntityLockControl entityLockControl;

    private List<FieldDefinition> specFieldDefinitions;
    private List<FieldDefinition> editFieldDefinitions;
    protected EditMode editMode = null;
    protected S spec = null;
    protected E edit = null;

    protected BaseEditCommand(CommandSecurityDefinition commandSecurityDefinition, List<FieldDefinition> specFieldDefinitions,
            List<FieldDefinition> editFieldDefinitions) {
        super(commandSecurityDefinition);

        this.specFieldDefinitions = specFieldDefinitions;
        this.editFieldDefinitions = editFieldDefinitions;
    }

    private void initForRun(BaseEditForm<S, E> editForm) {
        if(editForm != null) {
            editMode = editForm.getEditMode();
            spec = editForm.getSpec();
            edit = editForm.getEdit();
        }
    }

    public <Result extends BaseResult> Future<CommandResult<Result>> runAsync(UserVisitPK userVisitPK, BaseEditForm<S, E> editForm) {
        initForRun(editForm);

        return super.runAsync(userVisitPK);
    }

    public <Result extends BaseResult> CommandResult<Result> run(UserVisitPK userVisitPK, BaseEditForm<S, E> editForm) {
        initForRun(editForm);

        return super.run(userVisitPK);
    }

    protected void setupValidatorForSpec(Validator validator) {
        // Nothing.
    }

    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        // Nothing.
    }

    protected ValidationResult validateSpec(Validator validator) {
        return validator.validate(spec, getSpecFieldDefinitions());
    }

    protected ValidationResult validateEdit(Validator validator) {
        return validator.validate(edit, getEditFieldDefinitions());
    }

    protected ValidationResult validateLock() {
        var validator = new Validator(this);

        setupValidatorForSpec(validator);

        return validateSpec(validator);
    }

    protected ValidationResult validateUpdate() {
        var validator = new Validator(this);
        ValidationResult validationResult;
        BaseForm specForm = spec;

        setupValidatorForSpec(validator);
        validationResult = validateSpec(validator);

        if(!validationResult.getHasErrors()) {
            setupValidatorForEdit(validator, specForm);
            validationResult = validateEdit(validator);

            if(validationResult.getHasErrors()) {
                saveResultAfterEditValidatorErrors();
            }
        }

        return validationResult;
    }

    @Override
    protected ValidationResult validate() {
        ValidationResult validationResult = null;

        if(editMode.equals(EditMode.LOCK)) {
            validationResult = validateLock();
        } else if(editMode.equals(EditMode.UPDATE)) {
            validationResult = validateUpdate();
        }

        return validationResult;
    }

    protected List<FieldDefinition> getSpecFieldDefinitions() {
        return specFieldDefinitions;
    }

    protected void setSpecFieldDefinitions(List<FieldDefinition> specFieldDefinitions) {
        this.specFieldDefinitions = specFieldDefinitions;
    }

    protected List<FieldDefinition> getEditFieldDefinitions() {
        return editFieldDefinitions;
    }

    protected void setEditFieldDefinitions(List<FieldDefinition> editFieldDefinitions) {
        this.editFieldDefinitions = editFieldDefinitions;
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
                        // Build the current-value transfer before acquiring a lock, so a transfer
                        // construction failure cannot leave a newly acquired lock behind.
                        fillInResult(result, baseEntity);

                        if(!hasExecutionErrors()) {
                            if(lockEntity(lockEntity)) {
                                edit = getEdit();
                                result.setEdit(edit);
                                doLock(edit, baseEntity);
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                        }

                        result.setEntityLock(getEntityLockTransfer(lockEntity));
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

    protected void saveResultAfterEditValidatorErrors() {
        errorResult = getResult();

        var baseEntity = getEntity(errorResult);

        if(baseEntity != null) {
            var lockEntity = getLockEntity(baseEntity);

            fillInResult(errorResult, baseEntity, lockEntity);
        }
    }

    @Override
    protected BaseResult getBaseResultAfterErrors() {
        return errorResult;
    }

    /** Selects the entity represented by a lock target; metadata-backed editors may override this mapping. */
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return lockTarget.getPrimaryKey();
    }

    public EntityLockTransfer getEntityLockTransfer(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.getEntityLockTransfer(getUserVisit(), getLockTargetPK(lockTarget));
    }

    public EntityLockTransfer getEntityLockTransfer(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.getEntityLockTransfer(getUserVisit(), lockTarget);
    }

    public EntityLockTransfer getEntityLockTransfer(BaseValue<?> lockTarget)
            throws EntityLockException {
        return entityLockControl.getEntityLockTransfer(getUserVisit(), lockTarget);
    }

    public boolean lockEntity(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntity(getLockTargetPK(lockTarget), getPartyPK()) != 0;
    }

    public boolean lockEntity(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntity(lockTarget, getPartyPK()) != 0;
    }

    public boolean lockEntity(BaseValue<?> lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntity(lockTarget, getPartyPK()) != 0;
    }

    public boolean lockEntityForUpdate(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntityForUpdate(getLockTargetPK(lockTarget), getPartyPK());
    }

    public boolean lockEntityForUpdate(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntityForUpdate(lockTarget, getPartyPK());
    }

    public boolean lockEntityForUpdate(BaseValue<?> lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntityForUpdate(lockTarget, getPartyPK());
    }

    public boolean unlockEntity(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.unlockEntity(getLockTargetPK(lockTarget), getPartyPK());
    }

    public boolean unlockEntity(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.unlockEntity(lockTarget, getPartyPK());
    }

    public boolean unlockEntity(BaseValue<?> lockTarget)
            throws EntityLockException {
        return entityLockControl.unlockEntity(lockTarget, getPartyPK());
    }

    public boolean isEntityLocked(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.isEntityLocked(getLockTargetPK(lockTarget), getPartyPK());
    }

    public boolean isEntityLocked(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.isEntityLocked(lockTarget, getPartyPK());
    }

    public boolean isEntityLocked(BaseValue<?> lockTarget)
            throws EntityLockException {
        return entityLockControl.isEntityLocked(lockTarget, getPartyPK());
    }

}
