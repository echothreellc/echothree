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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityStringAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityStringAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityStringAttributeResult;
import com.echothree.control.user.core.common.spec.EntityStringAttributeSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.value.EntityStringAttributeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditEntityStringAttributeCommand
        extends BaseEditCommand<EntityStringAttributeSpec, EntityStringAttributeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUlid", FieldType.ULID, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageUlid", FieldType.ENTITY_NAME, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("StringAttribute", FieldType.STRING, true, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of EditEntityStringAttributeCommand */
    public EditEntityStringAttributeCommand(UserVisitPK userVisitPK, EditEntityStringAttributeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        EditEntityStringAttributeResult result = CoreResultFactory.getEditEntityStringAttributeResult();
        int parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec);

            if(!hasExecutionErrors()) {
                String entityAttributeName = spec.getEntityAttributeName();
                String entityAttributeUlid = spec.getEntityAttributeUlid();
                
                parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUlid == null ? 0 : 1);
                
                if(parameterCount == 1) {
                    EntityAttribute entityAttribute = entityAttributeName == null ?
                            EntityAttributeLogic.getInstance().getEntityAttributeByUlid(this, entityAttributeUlid) :
                            EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);

                    if(!hasExecutionErrors()) {
                        if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                            String languageIsoName = spec.getLanguageIsoName();
                            String languageUlid = spec.getLanguageUlid();
                            
                            parameterCount = (languageIsoName == null ? 0 : 1) + (languageUlid == null ? 0 : 1);

                            if(parameterCount == 1) {
                                Language language = languageIsoName == null ?
                                        LanguageLogic.getInstance().getLanguageByUlid(this, languageUlid) :
                                        LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);

                                if(!hasExecutionErrors()) {
                                    CoreControl coreControl = getCoreControl();
                                    EntityStringAttribute entityStringAttribute = null;
                                    BasePK basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                        entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language);

                                        if(entityStringAttribute != null) {
                                            if(editMode.equals(EditMode.LOCK)) {
                                                result.setEntityStringAttribute(coreControl.getEntityStringAttributeTransfer(getUserVisit(),
                                                        entityStringAttribute, entityInstance));

                                                if(lockEntity(basePK)) {
                                                    EntityStringAttributeEdit edit = CoreEditFactory.getEntityStringAttributeEdit();

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
                                        EntityAttributeString entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
                                        String validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();
                                        String stringAttribute = edit.getStringAttribute();

                                        if(validationPattern != null) {
                                            Pattern pattern = Pattern.compile(validationPattern);
                                            Matcher m = pattern.matcher(stringAttribute);

                                            if(!m.matches()) {
                                                addExecutionError(ExecutionErrors.InvalidStringAttribute.name(), stringAttribute);
                                            }
                                        }

                                        if(!hasExecutionErrors()) {
                                            entityStringAttribute = coreControl.getEntityStringAttributeForUpdate(entityAttribute, entityInstance, language);

                                            if(entityStringAttribute != null) {
                                                if(lockEntityForUpdate(basePK)) {
                                                    try {
                                                        EntityStringAttributeValue entityStringAttributeValue = coreControl.getEntityStringAttributeValueForUpdate(entityStringAttribute);

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
