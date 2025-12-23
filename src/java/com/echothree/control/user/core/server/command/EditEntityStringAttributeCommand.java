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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityStringAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityStringAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityStringAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
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
import java.util.regex.Pattern;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityStringAttributeCommand
        extends BaseEditCommand<EntityStringAttributeSpec, EntityStringAttributeEdit> {

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
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageUuid", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("StringAttribute", FieldType.STRING, true, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of EditEntityStringAttributeCommand */
    public EditEntityStringAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getEditEntityStringAttributeResult();
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
                            var languageIsoName = spec.getLanguageIsoName();
                            var languageUuid = spec.getLanguageUuid();
                            
                            parameterCount = (languageIsoName == null ? 0 : 1) + (languageUuid == null ? 0 : 1);

                            if(parameterCount == 1) {
                                var language = languageIsoName == null ?
                                        LanguageLogic.getInstance().getLanguageByUuid(this, languageUuid) :
                                        LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);

                                if(!hasExecutionErrors()) {
                                    EntityStringAttribute entityStringAttribute = null;
                                    var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                        entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language);

                                        if(entityStringAttribute != null) {
                                            if(editMode.equals(EditMode.LOCK)) {
                                                result.setEntityStringAttribute(coreControl.getEntityStringAttributeTransfer(getUserVisit(),
                                                        entityStringAttribute, entityInstance));

                                                if(lockEntity(basePK)) {
                                                    var edit = CoreEditFactory.getEntityStringAttributeEdit();

                                                    result.setEdit(edit);
                                                    edit.setStringAttribute(entityStringAttribute.getStringAttribute());
                                                } else {
                                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                                }
                                            } else { // EditMode.ABANDON
                                                unlockEntity(basePK);
                                                basePK = null;
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownEntityStringAttribute.name(),
                                                    EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance), entityAttributeName);
                                        }
                                    } else if(editMode.equals(EditMode.UPDATE)) {
                                        var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
                                        var validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();
                                        var stringAttribute = edit.getStringAttribute();

                                        if(validationPattern != null) {
                                            var pattern = Pattern.compile(validationPattern);
                                            var m = pattern.matcher(stringAttribute);

                                            if(!m.matches()) {
                                                addExecutionError(ExecutionErrors.InvalidStringAttribute.name(), stringAttribute);
                                            }
                                        }

                                        if(!hasExecutionErrors()) {
                                            entityStringAttribute = coreControl.getEntityStringAttributeForUpdate(entityAttribute, entityInstance, language);

                                            if(entityStringAttribute != null) {
                                                if(lockEntityForUpdate(basePK)) {
                                                    try {
                                                        var entityStringAttributeValue = coreControl.getEntityStringAttributeValueForUpdate(entityStringAttribute);

                                                        entityStringAttributeValue.setStringAttribute(stringAttribute);

                                                        coreControl.updateEntityStringAttributeFromValue(entityStringAttributeValue, getPartyPK());
                                                    } finally {
                                                        unlockEntity(basePK);
                                                        basePK = null;
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownEntityStringAttribute.name(),
                                                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance), entityAttributeName);
                                            }
                                        }
                                    }

                                    if(basePK != null) {
                                        result.setEntityLock(getEntityLockTransfer(basePK));
                                    }

                                    if(entityStringAttribute != null) {
                                        result.setEntityStringAttribute(coreControl.getEntityStringAttributeTransfer(getUserVisit(), entityStringAttribute, entityInstance));
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
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
