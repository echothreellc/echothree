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
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.exception.EntityLockException;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseEditForm;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.BaseSpec;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.BaseValue;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.List;
import java.util.concurrent.Future;
import javax.inject.Inject;

public abstract class BaseEditCommand<S extends BaseSpec, E extends BaseEdit>
        extends BaseCommand {

    @Inject
    EntityLockControl entityLockControl;

    private List<FieldDefinition> specFieldDefinitions;
    private List<FieldDefinition> editFieldDefinitions;
    protected EditMode editMode = null;
    protected S spec = null;
    protected E edit = null;
    
    protected BaseEditCommand(CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> specFieldDefinitions, List<FieldDefinition> editFieldDefinitions) {
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

    public Future<CommandResult> runAsync(UserVisitPK userVisitPK, BaseEditForm<S, E> editForm) {
        initForRun(editForm);

        return super.runAsync(userVisitPK);
    }

    public CommandResult run(UserVisitPK userVisitPK, BaseEditForm<S, E> editForm) {
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
    
    protected void saveResultAfterEditValidatorErrors() {
        // Nothing.
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
    
    public EntityLockTransfer getEntityLockTransfer(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.getEntityLockTransfer(getUserVisit(), lockTarget);
    }

    public EntityLockTransfer getEntityLockTransfer(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.getEntityLockTransfer(getUserVisit(), lockTarget);
    }

    public EntityLockTransfer getEntityLockTransfer(BaseValue lockTarget)
            throws EntityLockException {
        return entityLockControl.getEntityLockTransfer(getUserVisit(), lockTarget);
    }

    public boolean lockEntity(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntity(lockTarget, getPartyPK()) != 0;
    }

    public boolean lockEntity(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntity(lockTarget, getPartyPK()) != 0;
    }

    public boolean lockEntity(BaseValue lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntity(lockTarget, getPartyPK()) != 0;
    }

    public boolean lockEntityForUpdate(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntityForUpdate(lockTarget, getPartyPK());
    }

    public boolean lockEntityForUpdate(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntityForUpdate(lockTarget, getPartyPK());
    }

    public boolean lockEntityForUpdate(BaseValue lockTarget)
            throws EntityLockException {
        return entityLockControl.lockEntityForUpdate(lockTarget, getPartyPK());
    }

    public boolean unlockEntity(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.unlockEntity(lockTarget, getPartyPK());
    }

    public boolean unlockEntity(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.unlockEntity(lockTarget, getPartyPK());
    }

    public boolean unlockEntity(BaseValue lockTarget)
            throws EntityLockException {
        return entityLockControl.unlockEntity(lockTarget, getPartyPK());
    }

    public boolean isEntityLocked(BaseEntity lockTarget)
            throws EntityLockException {
        return entityLockControl.isEntityLocked(lockTarget, getPartyPK());
    }

    public boolean isEntityLocked(BasePK lockTarget)
            throws EntityLockException {
        return entityLockControl.isEntityLocked(lockTarget, getPartyPK());
    }

    public boolean isEntityLocked(BaseValue lockTarget)
            throws EntityLockException {
        return entityLockControl.isEntityLocked(lockTarget, getPartyPK());
    }
    
}
