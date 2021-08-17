// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.CreateEntityCollectionAttributeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityCollectionAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateEntityCollectionAttributeCommand
        extends BaseSimpleCommand<CreateEntityCollectionAttributeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUlid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityRefAttribute", FieldType.ENTITY_REF, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityCollectionAttributeCommand */
    public CreateEntityCollectionAttributeCommand(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

            if(!hasExecutionErrors()) {
                String entityAttributeName = form.getEntityAttributeName();
                String entityAttributeUlid = form.getEntityAttributeUlid();
                
                parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUlid == null ? 0 : 1);
                
                if(parameterCount == 1) {
                    EntityAttribute entityAttribute = entityAttributeName == null ?
                            EntityAttributeLogic.getInstance().getEntityAttributeByUlid(this, entityAttributeUlid) :
                            EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);

                    if(!hasExecutionErrors()) {
                        String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

                        if(EntityAttributeTypes.COLLECTION.name().equals(entityAttributeTypeName)) {
                            if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                                var coreControl = getCoreControl();
                                String entityRefAttribute = form.getEntityRefAttribute();
                                EntityInstance entityInstanceAttribute = coreControl.getEntityInstanceByEntityRef(entityRefAttribute);

                                if(entityInstanceAttribute != null) {
                                    EntityCollectionAttribute entityCollectionAttribute = coreControl.getEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute);

                                    if(entityCollectionAttribute == null) {
                                        if(coreControl.countEntityAttributeEntityTypesByEntityAttribute(entityAttribute) > 0) {
                                            EntityType allowedEntityType = entityInstanceAttribute.getEntityType();

                                            if(!coreControl.entityAttributeEntityTypeExists(entityAttribute, allowedEntityType)) {
                                                EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
                                                EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                                                EntityTypeDetail allowedEntityTypeDetail = allowedEntityType.getLastDetail();

                                                addExecutionError(ExecutionErrors.UnknownEntityAttributeEntityType.name(), 
                                                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                                        entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                                                        allowedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                                        allowedEntityTypeDetail.getEntityTypeName());
                                            }
                                        }

                                        if(!hasExecutionErrors()) {
                                            coreControl.createEntityCollectionAttribute(entityAttribute, entityInstance, entityInstanceAttribute, getPartyPK());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.DuplicateEntityCollectionAttribute.name(),
                                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                                entityAttribute.getLastDetail().getEntityAttributeName(),
                                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstanceAttribute));
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityRefAttribute.name(), entityRefAttribute);
                                }
                            } else {
                                EntityTypeDetail expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
                                EntityTypeDetail suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

                                addExecutionError(ExecutionErrors.MismatchedEntityType.name(),
                                        expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                        expectedEntityTypeDetail.getEntityTypeName(),
                                        suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                        suppliedEntityTypeDetail.getEntityTypeName());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MismatchedEntityAttributeType.name(),
                                    EntityAttributeTypes.COLLECTION.name(), entityAttributeTypeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
