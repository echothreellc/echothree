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
import com.echothree.control.user.core.common.edit.EntityClobAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityClobAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityClobAttributeSpec;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityClobAttributeCommand
        extends BaseEditCommand<EntityClobAttributeSpec, EntityClobAttributeEdit> {

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
                new FieldDefinition("LanguageUuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ClobAttribute", FieldType.STRING, true, 1L, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityClobAttributeCommand */
    public EditEntityClobAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getEditEntityClobAttributeResult();
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec);
        var language = LanguageLogic.getInstance().getLanguage(this, spec, spec);
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttribute(this, entityInstance, spec, spec,
                EntityAttributeTypes.CLOB);

        if(!hasExecutionErrors()) {
            var coreControl = getCoreControl();
            EntityClobAttribute entityClobAttribute = null;
            var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);

                if(entityClobAttribute != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setEntityClobAttribute(coreControl.getEntityClobAttributeTransfer(getUserVisit(), entityClobAttribute, entityInstance));

                        if(lockEntity(basePK)) {
                            var edit = CoreEditFactory.getEntityClobAttributeEdit();

                            result.setEdit(edit);
                            edit.setClobAttribute(entityClobAttribute.getClobAttribute());
                            edit.setMimeTypeName(entityClobAttribute.getMimeType().getLastDetail().getMimeTypeName());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                    } else { // EditMode.ABANDON
                        unlockEntity(basePK);
                        basePK = null;
                    }
                } else {
                    var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                    addExecutionError(ExecutionErrors.UnknownEntityClobAttribute.name(), basePK.getEntityRef(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(),
                            language.getLanguageIsoName());
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                entityClobAttribute = coreControl.getEntityClobAttributeForUpdate(entityAttribute, entityInstance, language);

                if(entityClobAttribute != null) {
                    var mimeType = MimeTypeLogic.getInstance().getMimeTypeByName(this, edit.getMimeTypeName());

                    if(!hasExecutionErrors()) {
                        if(mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.CLOB.name())) {
                            if(lockEntityForUpdate(basePK)) {
                                try {
                                    var entityClobAttributeValue = coreControl.getEntityClobAttributeValueForUpdate(entityClobAttribute);

                                    entityClobAttributeValue.setClobAttribute(edit.getClobAttribute());
                                    entityClobAttributeValue.setMimeTypePK(mimeType.getPrimaryKey());

                                    coreControl.updateEntityClobAttributeFromValue(entityClobAttributeValue, getPartyPK());
                                } finally {
                                    unlockEntity(basePK);
                                    basePK = null;
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidMimeType.name(), mimeType.getLastDetail().getMimeTypeName());
                        }
                    }
                } else {
                    var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

                    addExecutionError(ExecutionErrors.UnknownEntityClobAttribute.name(), basePK.getEntityRef(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(),
                            language.getLanguageIsoName());
                }
            }

            if(basePK != null) {
                result.setEntityLock(getEntityLockTransfer(basePK));
            }

            if(entityClobAttribute != null) {
                result.setEntityClobAttribute(coreControl.getEntityClobAttributeTransfer(getUserVisit(), entityClobAttribute, entityInstance));
            }
        }

        return result;
    }
    
}
