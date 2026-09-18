// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.EntityAttributeGroupEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupUniversalSpec;
import com.echothree.model.control.core.server.control.EntityAttributeGroupControl;
import com.echothree.model.control.core.server.logic.EntityAttributeGroupLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityAttributeGroupCommand
        extends BaseAbstractEditCommand<EntityAttributeGroupUniversalSpec, EntityAttributeGroupEdit, EditEntityAttributeGroupResult, EntityAttributeGroup, EntityAttributeGroup> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeGroup.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    @Inject
    protected EntityAttributeGroupControl entityAttributeGroupControl;

    @Inject
    protected EntityAttributeGroupLogic entityAttributeGroupLogic;

    /** Creates a new instance of EditEntityAttributeGroupCommand */
    public EditEntityAttributeGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityAttributeGroupResult getResult() {
        return CoreResultFactory.getEditEntityAttributeGroupResult();
    }
    
    @Override
    public EntityAttributeGroupEdit getEdit() {
        return CoreEditFactory.getEntityAttributeGroupEdit();
    }
    
    @Override
    public EntityAttributeGroup getEntity(EditEntityAttributeGroupResult result) {
        return entityAttributeGroupLogic.getEntityAttributeGroupByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }
    
    @Override
    public EntityAttributeGroup getLockEntity(EntityAttributeGroup entityAttributeGroup) {
        return entityAttributeGroup;
    }
    
    @Override
    public void fillInResult(EditEntityAttributeGroupResult result, EntityAttributeGroup entityAttributeGroup) {
        
        result.setEntityAttributeGroup(entityAttributeGroupControl.getEntityAttributeGroupTransfer(getUserVisit(), entityAttributeGroup, null));
    }
    
    @Override
    public void doLock(EntityAttributeGroupEdit edit, EntityAttributeGroup entityAttributeGroup) {
        var entityAttributeGroupDescription = entityAttributeGroupControl.getEntityAttributeGroupDescription(entityAttributeGroup, getPreferredLanguage());
        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

        edit.setEntityAttributeGroupName(entityAttributeGroupDetail.getEntityAttributeGroupName());
        edit.setIsDefault(entityAttributeGroupDetail.getIsDefault().toString());
        edit.setSortOrder(entityAttributeGroupDetail.getSortOrder().toString());

        if(entityAttributeGroupDescription != null) {
            edit.setDescription(entityAttributeGroupDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(EntityAttributeGroup entityAttributeGroup) {
        var entityAttributeGroupName = edit.getEntityAttributeGroupName();
        var duplicateEntityAttributeGroup = entityAttributeGroupControl.getEntityAttributeGroupByName(entityAttributeGroup.getLastDetail().getEntityType(), entityAttributeGroupName);

        if(duplicateEntityAttributeGroup != null && !entityAttributeGroup.equals(duplicateEntityAttributeGroup)) {
            var entityTypeDetail = entityAttributeGroup.getLastDetail().getEntityType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateEntityAttributeGroupName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttributeGroupName);
        }
    }
    
    @Override
    public void doUpdate(EntityAttributeGroup entityAttributeGroup) {
        var partyPK = getPartyPK();
        var entityAttributeGroupDetailValue = entityAttributeGroupControl.getEntityAttributeGroupDetailValueForUpdate(entityAttributeGroup);
        var entityAttributeGroupDescription = entityAttributeGroupControl.getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, getPreferredLanguage());
        var description = edit.getDescription();

        entityAttributeGroupDetailValue.setEntityAttributeGroupName(edit.getEntityAttributeGroupName());
        entityAttributeGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityAttributeGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        entityAttributeGroupControl.updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, partyPK);

        if(entityAttributeGroupDescription == null && description != null) {
            entityAttributeGroupControl.createEntityAttributeGroupDescription(entityAttributeGroup, getPreferredLanguage(), description, partyPK);
        } else if(entityAttributeGroupDescription != null && description == null) {
            entityAttributeGroupControl.deleteEntityAttributeGroupDescription(entityAttributeGroupDescription, partyPK);
        } else if(entityAttributeGroupDescription != null && description != null) {
            var entityAttributeGroupDescriptionValue = entityAttributeGroupControl.getEntityAttributeGroupDescriptionValue(entityAttributeGroupDescription);

            entityAttributeGroupDescriptionValue.setDescription(description);
            entityAttributeGroupControl.updateEntityAttributeGroupDescriptionFromValue(entityAttributeGroupDescriptionValue, partyPK);
        }
    }
    
}
