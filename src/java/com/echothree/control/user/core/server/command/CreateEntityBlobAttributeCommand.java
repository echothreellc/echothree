// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.CreateEntityBlobAttributeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateEntityBlobAttributeCommand
        extends BaseSimpleCommand<CreateEntityBlobAttributeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUlid", FieldType.ULID, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageUlid", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityBlobAttributeCommand */
    public CreateEntityBlobAttributeCommand(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

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

                        if(EntityAttributeTypes.BLOB.name().equals(entityAttributeTypeName)) {
                            if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                                String languageIsoName = form.getLanguageIsoName();
                                String languageUlid = form.getLanguageUlid();

                                parameterCount = (languageIsoName == null ? 0 : 1) + (languageUlid == null ? 0 : 1);

                                if(parameterCount == 1) {
                                    Language language = languageIsoName == null ?
                                            LanguageLogic.getInstance().getLanguageByUlid(this, languageUlid) :
                                            LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);

                                    if(!hasExecutionErrors()) {
                                        var coreControl = getCoreControl();
                                        EntityBlobAttribute entityBlobAttribute = coreControl.getEntityBlobAttribute(entityAttribute, entityInstance, language);

                                        if(entityBlobAttribute == null) {
                                            MimeType mimeType = MimeTypeLogic.getInstance().getMimeTypeByName(this, form.getMimeTypeName());

                                            if(!hasExecutionErrors()) {
                                                if(mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.BLOB.name())) {
                                                    ByteArray blobAttribute = form.getBlobAttribute();

                                                    if(blobAttribute != null) {
                                                        coreControl.createEntityBlobAttribute(entityAttribute, entityInstance, language, blobAttribute, mimeType, getPartyPK());
                                                    } else {
                                                        addExecutionError(ExecutionErrors.MissingBlobAttribute.name());
                                                    }
                                                } else {
                                                    addExecutionError(ExecutionErrors.InvalidMimeType.name(), mimeType.getLastDetail().getMimeTypeName());
                                                }
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.DuplicateEntityBlobAttribute.name(),
                                                    EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                                    entityAttribute.getLastDetail().getEntityAttributeName(),
                                                    language.getLanguageIsoName());
                                        }
                                    }
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
                                    EntityAttributeTypes.BLOB.name(), entityAttributeTypeName);
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
