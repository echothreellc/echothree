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

import com.echothree.control.user.core.remote.edit.CoreEditFactory;
import com.echothree.control.user.core.remote.edit.EntityAttributeEntityAttributeGroupEdit;
import com.echothree.control.user.core.remote.form.EditEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.EditEntityAttributeEntityAttributeGroupResult;
import com.echothree.control.user.core.remote.spec.EntityAttributeEntityAttributeGroupSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityAttributeEntityAttributeGroupValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityAttributeEntityAttributeGroupCommand
        extends BaseAbstractEditCommand<EntityAttributeEntityAttributeGroupSpec, EntityAttributeEntityAttributeGroupEdit, EditEntityAttributeEntityAttributeGroupResult, EntityAttributeEntityAttributeGroup, EntityAttribute> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttribute.name(), SecurityRoles.EntityAttributeEntityAttributeGroup.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditEntityAttributeEntityAttributeGroupCommand */
    public EditEntityAttributeEntityAttributeGroupCommand(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityAttributeEntityAttributeGroupResult getResult() {
        return CoreResultFactory.getEditEntityAttributeEntityAttributeGroupResult();
    }

    @Override
    public EntityAttributeEntityAttributeGroupEdit getEdit() {
        return CoreEditFactory.getEntityAttributeEntityAttributeGroupEdit();
    }

    @Override
    public EntityAttributeEntityAttributeGroup getEntity(EditEntityAttributeEntityAttributeGroupResult result) {
        EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup = null;
        EntityAttribute entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this, spec.getComponentVendorName(), spec.getEntityTypeName(), spec.getEntityAttributeName());
        
        if(!hasExecutionErrors()) {
            EntityAttributeGroup entityAttributeGroup = EntityAttributeLogic.getInstance().getEntityAttributeGroupByName(this, spec.getEntityAttributeGroupName());
            
            if(!hasExecutionErrors()) {
                CoreControl coreControl = getCoreControl();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    entityAttributeEntityAttributeGroup = coreControl.getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup);
                } else { // EditMode.UPDATE
                    entityAttributeEntityAttributeGroup = coreControl.getEntityAttributeEntityAttributeGroupForUpdate(entityAttribute, entityAttributeGroup);
                }

                if(entityAttributeEntityAttributeGroup == null) {
                    EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
                    EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                    
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeEntityAttributeGroup.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                            entityAttributeDetail.getEntityAttributeName(), entityAttributeGroup.getLastDetail().getEntityAttributeGroupName());
                }
            }
        }
        
        return entityAttributeEntityAttributeGroup;
    }

    @Override
    public EntityAttribute getLockEntity(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        return entityAttributeEntityAttributeGroup.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityAttributeEntityAttributeGroupResult result, EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        CoreControl coreControl = getCoreControl();

        result.setEntityAttributeEntityAttributeGroup(coreControl.getEntityAttributeEntityAttributeGroupTransfer(getUserVisit(), entityAttributeEntityAttributeGroup, null));
    }

    @Override
    public void doLock(EntityAttributeEntityAttributeGroupEdit edit, EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        edit.setSortOrder(entityAttributeEntityAttributeGroup.getSortOrder().toString());
    }

    @Override
    public void doUpdate(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        CoreControl coreControl = getCoreControl();
        EntityAttributeEntityAttributeGroupValue entityAttributeEntityAttributeGroupValue = coreControl.getEntityAttributeEntityAttributeGroupValue(entityAttributeEntityAttributeGroup);
        
        entityAttributeEntityAttributeGroupValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateEntityAttributeEntityAttributeGroupFromValue(entityAttributeEntityAttributeGroupValue, getPartyPK());
    }
    
}
