// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.EntityBlobAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityBlobAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityBlobAttributeResult;
import com.echothree.control.user.core.common.spec.EntityBlobAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.value.EntityBlobAttributeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityBlobAttributeCommand
        extends BaseEditCommand<EntityBlobAttributeSpec, EntityBlobAttributeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityBlobAttributeCommand */
    public EditEntityBlobAttributeCommand(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        EditEntityBlobAttributeResult result = CoreResultFactory.getEditEntityBlobAttributeResult();
        String entityRef = spec.getEntityRef();
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance != null) {
            String entityAttributeName = spec.getEntityAttributeName();
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);

            if(entityAttribute != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    EntityBlobAttribute entityBlobAttribute = null;
                    BasePK basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);
                    
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        entityBlobAttribute = coreControl.getEntityBlobAttribute(entityAttribute, entityInstance, language);

                        if(entityBlobAttribute != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                result.setEntityBlobAttribute(coreControl.getEntityBlobAttributeTransfer(getUserVisit(),
                                        entityBlobAttribute, entityInstance));

                                if(lockEntity(basePK)) {
                                    EntityBlobAttributeEdit edit = CoreEditFactory.getEntityBlobAttributeEdit();

                                    result.setEdit(edit);
                                    edit.setMimeTypeName(entityBlobAttribute.getMimeType().getLastDetail().getMimeTypeName());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                            } else { // EditMode.ABANDON
                                unlockEntity(basePK);
                                basePK = null;
                            }
                        } else {
                            EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                            addExecutionError(ExecutionErrors.UnknownEntityBlobAttribute.name(), entityRef,
                                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                    entityTypeDetail.getEntityTypeName(), entityAttributeName, languageIsoName);
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        entityBlobAttribute = coreControl.getEntityBlobAttributeForUpdate(entityAttribute, entityInstance, language);

                        if(entityBlobAttribute != null) {
                            MimeType mimeType = MimeTypeLogic.getInstance().getMimeTypeByName(this, edit.getMimeTypeName());

                            if(!hasExecutionErrors()) {
                                if(mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.BLOB.name())) {
                                    ByteArray blobAttribute = edit.getBlobAttribute();

                                    if(blobAttribute != null) {
                                        if(lockEntityForUpdate(basePK)) {
                                            try {
                                                EntityBlobAttributeValue entityBlobAttributeValue = coreControl.getEntityBlobAttributeValueForUpdate(entityBlobAttribute);

                                                entityBlobAttributeValue.setBlobAttribute(blobAttribute);
                                                entityBlobAttributeValue.setMimeTypePK(mimeType.getPrimaryKey());

                                                coreControl.updateEntityBlobAttributeFromValue(entityBlobAttributeValue, getPartyPK());
                                            } finally {
                                                unlockEntity(basePK);
                                                basePK = null;
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.MissingBlobAttribute.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidMimeType.name(), mimeType.getLastDetail().getMimeTypeName());
                                }
                            }
                        } else {
                            EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                            addExecutionError(ExecutionErrors.UnknownEntityBlobAttribute.name(), entityRef,
                                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                    entityTypeDetail.getEntityTypeName(), entityAttributeName, languageIsoName);
                        }
                    }
                    
                    if(basePK != null) {
                        result.setEntityLock(getEntityLockTransfer(basePK));
                    }

                    if(entityBlobAttribute != null) {
                        result.setEntityBlobAttribute(coreControl.getEntityBlobAttributeTransfer(getUserVisit(), entityBlobAttribute, entityInstance));
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }

        return result;
    }
    
}
