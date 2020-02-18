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
import com.echothree.control.user.core.common.edit.EntityTypeEdit;
import com.echothree.control.user.core.common.form.EditEntityTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityTypeResult;
import com.echothree.control.user.core.common.spec.EntityTypeSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDescription;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityTypeCommand
        extends BaseAbstractEditCommand<EntityTypeSpec, EntityTypeEdit, EditEntityTypeResult, EntityType, EntityType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
                ));
                
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("KeepAllHistory", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("LockTimeout", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("LockTimeoutUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEntityTypeCommand */
    public EditEntityTypeCommand(UserVisitPK userVisitPK, EditEntityTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
   public EditEntityTypeResult getResult() {
        return CoreResultFactory.getEditEntityTypeResult();
    }

    @Override
    public EntityTypeEdit getEdit() {
        return CoreEditFactory.getEntityTypeEdit();
    }

    ComponentVendor componentVendor = null;
    
    @Override
    public EntityType getEntity(EditEntityTypeResult result) {
        var coreControl = getCoreControl();
        EntityType entityType = null;
        String componentVendorName = spec.getComponentVendorName();
        
        componentVendor = coreControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            } else { // EditMode.UPDATE
                entityType = coreControl.getEntityTypeByNameForUpdate(componentVendor, entityTypeName);
            }

            if(entityType == null) {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityType;
    }

    @Override
    public EntityType getLockEntity(EntityType entityType) {
        return entityType;
    }

    @Override
    public void fillInResult(EditEntityTypeResult result, EntityType entityType) {
        var coreControl = getCoreControl();

        result.setEntityType(coreControl.getEntityTypeTransfer(getUserVisit(), entityType));
    }

    @Override
    public void doLock(EntityTypeEdit edit, EntityType entityType) {
        var coreControl = getCoreControl();
        UnitOfMeasureTypeLogic unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
        EntityTypeDescription entityTypeDescription = coreControl.getEntityTypeDescription(entityType, getPreferredLanguage());
        EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
        UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;

        edit.setEntityTypeName(entityTypeDetail.getEntityTypeName());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, entityTypeDetail.getLockTimeout());
        edit.setLockTimeoutUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setLockTimeout(stringUnitOfMeasure.getValue());
        edit.setKeepAllHistory(entityTypeDetail.getKeepAllHistory().toString());
        edit.setSortOrder(entityTypeDetail.getSortOrder().toString());

        if(entityTypeDescription != null) {
            edit.setDescription(entityTypeDescription.getDescription());
        }
    }

    Long lockTimeout;

    @Override
    public void canUpdate(EntityType entityType) {
        var coreControl = getCoreControl();
        String entityTypeName = edit.getEntityTypeName();
        EntityType duplicateEntityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

        if(duplicateEntityType != null && !entityType.equals(duplicateEntityType)) {
            addExecutionError(ExecutionErrors.DuplicateEntityTypeName.name(), entityTypeName);
        } else {
            UnitOfMeasureTypeLogic unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();

            lockTimeout = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                    edit.getLockTimeout(), edit.getLockTimeoutUnitOfMeasureTypeName(),
                    null, ExecutionErrors.MissingRequiredLockTimeout.name(), null, ExecutionErrors.MissingRequiredLockTimeoutUnitOfMeasureTypeName.name(),
                    null, ExecutionErrors.UnknownLockTimeoutUnitOfMeasureTypeName.name());

        }
    }

    @Override
    public void doUpdate(EntityType entityType) {
        var coreControl = getCoreControl();
        PartyPK partyPK = getPartyPK();
        EntityTypeDetailValue entityTypeDetailValue = coreControl.getEntityTypeDetailValueForUpdate(entityType);
        EntityTypeDescription entityTypeDescription = coreControl.getEntityTypeDescriptionForUpdate(entityType, getPreferredLanguage());
        String description = edit.getDescription();

        entityTypeDetailValue.setEntityTypeName(edit.getEntityTypeName());
        entityTypeDetailValue.setKeepAllHistory(Boolean.valueOf(edit.getKeepAllHistory()));
        entityTypeDetailValue.setLockTimeout(lockTimeout);
        entityTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateEntityTypeFromValue(entityTypeDetailValue, partyPK);

        if(entityTypeDescription == null && description != null) {
            coreControl.createEntityTypeDescription(entityType, getPreferredLanguage(), description, partyPK);
        } else {
            if(entityTypeDescription != null && description == null) {
                coreControl.deleteEntityTypeDescription(entityTypeDescription, partyPK);
            } else {
                if(entityTypeDescription != null && description != null) {
                    EntityTypeDescriptionValue entityTypeDescriptionValue = coreControl.getEntityTypeDescriptionValue(entityTypeDescription);

                    entityTypeDescriptionValue.setDescription(description);
                    coreControl.updateEntityTypeDescriptionFromValue(entityTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
