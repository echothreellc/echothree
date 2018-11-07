// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.DeleteEntityCollectionAttributeForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
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

public class DeleteEntityCollectionAttributeCommand
        extends BaseSimpleCommand<DeleteEntityCollectionAttributeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityRefAttribute", FieldType.ENTITY_REF, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteEntityCollectionAttributeCommand */
    public DeleteEntityCollectionAttributeCommand(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        String entityRef = form.getEntityRef();
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
        
        if(entityInstance != null) {
            String entityAttributeName = form.getEntityAttributeName();
            EntityType entityType = entityInstance.getEntityType();
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);
            
            if(entityAttribute != null) {
                String entityRefAttribute = form.getEntityRefAttribute();
                EntityInstance entityInstanceAttribute = coreControl.getEntityInstanceByEntityRef(entityRefAttribute);

                if(entityInstanceAttribute != null) {
                    EntityCollectionAttribute entityCollectionAttribute = coreControl.getEntityCollectionAttributeForUpdate(entityAttribute, entityInstance, entityInstanceAttribute);
                    
                    if(entityCollectionAttribute != null) {
                        coreControl.deleteEntityCollectionAttribute(entityCollectionAttribute, getPartyPK());
                    } else {
                        EntityTypeDetail entityTypeDetail = entityType.getLastDetail();

                        addExecutionError(ExecutionErrors.UnknownEntityCollectionAttribute.name(),
                                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                entityAttributeName, entityRef, entityRefAttribute);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityRefAttribute.name(), entityRefAttribute);
                }
            } else {
                EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
                
                addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }
        
        return null;
    }
    
}
