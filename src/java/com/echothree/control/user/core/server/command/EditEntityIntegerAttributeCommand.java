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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityIntegerAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityIntegerAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityIntegerAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityIntegerAttributeCommand
        extends BaseEditCommand<EntityIntegerAttributeSpec, EntityIntegerAttributeEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IntegerAttribute", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityIntegerAttributeCommand */
    public EditEntityIntegerAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getEditEntityIntegerAttributeResult();
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec);

            if(!hasExecutionErrors()) {
                var entityAttributeName = spec.getEntityAttributeName();
                var entityAttributeUuid = spec.getEntityAttributeUuid();
                
                parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUuid == null ? 0 : 1);
                
                if(parameterCount == 1) {
                    var entityAttribute = entityAttributeName == null ?
                            EntityAttributeLogic.getInstance().getEntityAttributeByUuid(this, entityAttributeUuid) :
                            EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);

                    if(!hasExecutionErrors()) {
                        if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                            var coreControl = getCoreControl();
                            EntityIntegerAttribute entityIntegerAttribute = null;
                            var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);

                                if(entityIntegerAttribute != null) {
                                    if(editMode.equals(EditMode.LOCK)) {
                                        result.setEntityIntegerAttribute(coreControl.getEntityIntegerAttributeTransfer(getUserVisit(), entityIntegerAttribute, entityInstance));

                                        if(lockEntity(basePK)) {
                                            var edit = CoreEditFactory.getEntityIntegerAttributeEdit();

                                            result.setEdit(edit);
                                            edit.setIntegerAttribute(entityIntegerAttribute.getIntegerAttribute().toString());
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                        }
                                    } else { // EditMode.ABANDON
                                        unlockEntity(basePK);
                                        basePK = null;
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityIntegerAttribute.name(),
                                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance), entityAttributeName);
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                var entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);
                                var integerAttribute = Integer.valueOf(edit.getIntegerAttribute());

                                if(entityAttributeInteger != null) {
                                    var upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
                                    var lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();

                                    if(upperRangeIntegerValue != null && integerAttribute > upperRangeIntegerValue){
                                        addExecutionError(ExecutionErrors.UpperRangeExceeded.name(),
                                                upperRangeIntegerValue, integerAttribute);
                                    }

                                    if(lowerRangeIntegerValue != null && integerAttribute < lowerRangeIntegerValue) {
                                        addExecutionError(ExecutionErrors.LowerRangeExceeded.name(),
                                                lowerRangeIntegerValue, integerAttribute);
                                    }
                                }

                                if(!hasExecutionErrors()) {
                                    entityIntegerAttribute = coreControl.getEntityIntegerAttributeForUpdate(entityAttribute, entityInstance);

                                    if(entityIntegerAttribute != null) {
                                        if(lockEntityForUpdate(basePK)) {
                                            try {
                                                var entityIntegerAttributeValue = coreControl.getEntityIntegerAttributeValueForUpdate(entityIntegerAttribute);

                                                entityIntegerAttributeValue.setIntegerAttribute(integerAttribute);

                                                coreControl.updateEntityIntegerAttributeFromValue(entityIntegerAttributeValue, getPartyPK());
                                            } finally {
                                                unlockEntity(basePK);
                                                basePK = null;
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownEntityIntegerAttribute.name(),
                                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                                entityAttribute.getLastDetail().getEntityAttributeName());
                                    }
                                }
                            }

                            if(basePK != null) {
                                result.setEntityLock(getEntityLockTransfer(basePK));
                            }

                            if(entityIntegerAttribute != null) {
                                result.setEntityIntegerAttribute(coreControl.getEntityIntegerAttributeTransfer(getUserVisit(), entityIntegerAttribute, entityInstance));
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MismatchedEntityType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
