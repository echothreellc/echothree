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
import com.echothree.control.user.core.common.edit.EntityLongRangeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityLongRangeDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityLongRangeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEntityLongRangeDescriptionCommand
        extends BaseAbstractEditCommand<EntityLongRangeDescriptionSpec, EntityLongRangeDescriptionEdit, EditEntityLongRangeDescriptionResult, EntityLongRangeDescription, EntityLongRange> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityLongRange.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityLongRangeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityLongRangeDescriptionCommand */
    public EditEntityLongRangeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityLongRangeDescriptionResult getResult() {
        return CoreResultFactory.getEditEntityLongRangeDescriptionResult();
    }

    @Override
    public EntityLongRangeDescriptionEdit getEdit() {
        return CoreEditFactory.getEntityLongRangeDescriptionEdit();
    }

    @Override
    public EntityLongRangeDescription getEntity(EditEntityLongRangeDescriptionResult result) {
        var coreControl = getCoreControl();
        EntityLongRangeDescription entityLongRangeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = spec.getEntityAttributeName();
                var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var entityLongRangeName = spec.getEntityLongRangeName();
                    var entityLongRange = coreControl.getEntityLongRangeByName(entityAttribute, entityLongRangeName);

                    if(entityLongRange != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                entityLongRangeDescription = coreControl.getEntityLongRangeDescription(entityLongRange, language);
                            } else { // EditMode.UPDATE
                                entityLongRangeDescription = coreControl.getEntityLongRangeDescriptionForUpdate(entityLongRange, language);
                            }

                            if(entityLongRangeDescription == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityLongRangeDescription.name(), componentVendorName, entityTypeName, entityAttributeName,
                                        entityLongRangeName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityLongRangeName.name(), componentVendorName, entityTypeName, entityAttributeName, entityLongRangeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), componentVendorName, entityTypeName, entityAttributeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityLongRangeDescription;
    }

    @Override
    public EntityLongRange getLockEntity(EntityLongRangeDescription entityLongRangeDescription) {
        return entityLongRangeDescription.getEntityLongRange();
    }

    @Override
    public void fillInResult(EditEntityLongRangeDescriptionResult result, EntityLongRangeDescription entityLongRangeDescription) {
        var coreControl = getCoreControl();

        result.setEntityLongRangeDescription(coreControl.getEntityLongRangeDescriptionTransfer(getUserVisit(), entityLongRangeDescription, null));
    }

    @Override
    public void doLock(EntityLongRangeDescriptionEdit edit, EntityLongRangeDescription entityLongRangeDescription) {
        edit.setDescription(entityLongRangeDescription.getDescription());
    }

    @Override
    public void doUpdate(EntityLongRangeDescription entityLongRangeDescription) {
        var coreControl = getCoreControl();
        var entityLongRangeDescriptionValue = coreControl.getEntityLongRangeDescriptionValue(entityLongRangeDescription);
        
        entityLongRangeDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateEntityLongRangeDescriptionFromValue(entityLongRangeDescriptionValue, getPartyPK());
    }
    
}
