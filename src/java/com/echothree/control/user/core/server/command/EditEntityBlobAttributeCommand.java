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
import com.echothree.control.user.core.common.edit.EntityBlobAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityBlobAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityBlobAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityBlobAttributeCommand
        extends BaseEditCommand<EntityBlobAttributeSpec, EntityBlobAttributeEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

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
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var result = CoreResultFactory.getEditEntityBlobAttributeResult();
        var entityRef = spec.getEntityRef();
        var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance != null) {
            var coreControl = getCoreControl();
            var entityAttributeName = spec.getEntityAttributeName();
            var entityAttribute = coreControl.getEntityAttributeByName(entityInstance.getEntityType(), entityAttributeName);

            if(entityAttribute != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    EntityBlobAttribute entityBlobAttribute = null;
                    var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);
                    
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        entityBlobAttribute = coreControl.getEntityBlobAttribute(entityAttribute, entityInstance, language);

                        if(entityBlobAttribute != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                result.setEntityBlobAttribute(coreControl.getEntityBlobAttributeTransfer(getUserVisit(),
                                        entityBlobAttribute, entityInstance));

                                if(lockEntity(basePK)) {
                                    var edit = CoreEditFactory.getEntityBlobAttributeEdit();

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
                            var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                            addExecutionError(ExecutionErrors.UnknownEntityBlobAttribute.name(), entityRef,
                                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                    entityTypeDetail.getEntityTypeName(), entityAttributeName, languageIsoName);
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        entityBlobAttribute = coreControl.getEntityBlobAttributeForUpdate(entityAttribute, entityInstance, language);

                        if(entityBlobAttribute != null) {
                            var mimeType = MimeTypeLogic.getInstance().getMimeTypeByName(this, edit.getMimeTypeName());

                            if(!hasExecutionErrors()) {
                                if(mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.BLOB.name())) {
                                    var blobAttribute = edit.getBlobAttribute();

                                    if(blobAttribute != null) {
                                        if(lockEntityForUpdate(basePK)) {
                                            try {
                                                var entityBlobAttributeValue = coreControl.getEntityBlobAttributeValueForUpdate(entityBlobAttribute);

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
                            var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

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
                var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }

        return result;
    }
    
}
