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
import com.echothree.control.user.core.common.edit.EntityIntegerRangeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityIntegerRangeDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityIntegerRangeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityIntegerRangeDescriptionCommand
        extends BaseAbstractEditCommand<EntityIntegerRangeDescriptionSpec, EntityIntegerRangeDescriptionEdit, EditEntityIntegerRangeDescriptionResult, EntityIntegerRangeDescription, EntityIntegerRange> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityIntegerRange.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityIntegerRangeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityIntegerRangeDescriptionCommand */
    public EditEntityIntegerRangeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityIntegerRangeDescriptionResult getResult() {
        return CoreResultFactory.getEditEntityIntegerRangeDescriptionResult();
    }

    @Override
    public EntityIntegerRangeDescriptionEdit getEdit() {
        return CoreEditFactory.getEntityIntegerRangeDescriptionEdit();
    }

    @Override
    public EntityIntegerRangeDescription getEntity(EditEntityIntegerRangeDescriptionResult result) {
        EntityIntegerRangeDescription entityIntegerRangeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = spec.getEntityAttributeName();
                var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var entityIntegerRangeName = spec.getEntityIntegerRangeName();
                    var entityIntegerRange = coreControl.getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName);

                    if(entityIntegerRange != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                entityIntegerRangeDescription = coreControl.getEntityIntegerRangeDescription(entityIntegerRange, language);
                            } else { // EditMode.UPDATE
                                entityIntegerRangeDescription = coreControl.getEntityIntegerRangeDescriptionForUpdate(entityIntegerRange, language);
                            }

                            if(entityIntegerRangeDescription == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityIntegerRangeDescription.name(), componentVendorName, entityTypeName, entityAttributeName,
                                        entityIntegerRangeName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityIntegerRangeName.name(), componentVendorName, entityTypeName, entityAttributeName, entityIntegerRangeName);
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

        return entityIntegerRangeDescription;
    }

    @Override
    public EntityIntegerRange getLockEntity(EntityIntegerRangeDescription entityIntegerRangeDescription) {
        return entityIntegerRangeDescription.getEntityIntegerRange();
    }

    @Override
    public void fillInResult(EditEntityIntegerRangeDescriptionResult result, EntityIntegerRangeDescription entityIntegerRangeDescription) {

        result.setEntityIntegerRangeDescription(coreControl.getEntityIntegerRangeDescriptionTransfer(getUserVisit(), entityIntegerRangeDescription, null));
    }

    @Override
    public void doLock(EntityIntegerRangeDescriptionEdit edit, EntityIntegerRangeDescription entityIntegerRangeDescription) {
        edit.setDescription(entityIntegerRangeDescription.getDescription());
    }

    @Override
    public void doUpdate(EntityIntegerRangeDescription entityIntegerRangeDescription) {
        var entityIntegerRangeDescriptionValue = coreControl.getEntityIntegerRangeDescriptionValue(entityIntegerRangeDescription);
        
        entityIntegerRangeDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateEntityIntegerRangeDescriptionFromValue(entityIntegerRangeDescriptionValue, getPartyPK());
    }
    
}
