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
import com.echothree.control.user.core.common.edit.EntityLongRangeEdit;
import com.echothree.control.user.core.common.form.EditEntityLongRangeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityLongRangeResult;
import com.echothree.control.user.core.common.spec.EntityLongRangeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityLongRange;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityLongRangeCommand
        extends BaseAbstractEditCommand<EntityLongRangeSpec, EntityLongRangeEdit, EditEntityLongRangeResult, EntityLongRange, EntityLongRange> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityLongRange.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityLongRangeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityLongRangeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumLongValue", FieldType.SIGNED_LONG, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityLongRangeCommand */
    public EditEntityLongRangeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
   public EditEntityLongRangeResult getResult() {
        return CoreResultFactory.getEditEntityLongRangeResult();
    }

    @Override
    public EntityLongRangeEdit getEdit() {
        return CoreEditFactory.getEntityLongRangeEdit();
    }

    EntityAttribute entityAttribute = null;
    
    @Override
    public EntityLongRange getEntity(EditEntityLongRangeResult result) {
        EntityLongRange entityLongRange = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = spec.getEntityAttributeName();
                
                entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var entityLongRangeName = spec.getEntityLongRangeName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        entityLongRange = coreControl.getEntityLongRangeByName(entityAttribute, entityLongRangeName);
                    } else { // EditMode.UPDATE
                        entityLongRange = coreControl.getEntityLongRangeByNameForUpdate(entityAttribute, entityLongRangeName);
                    }

                    if(entityLongRange == null) {
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

        return entityLongRange;
    }

    @Override
    public EntityLongRange getLockEntity(EntityLongRange entityLongRange) {
        return entityLongRange;
    }

    @Override
    public void fillInResult(EditEntityLongRangeResult result, EntityLongRange entityLongRange) {

        result.setEntityLongRange(coreControl.getEntityLongRangeTransfer(getUserVisit(), entityLongRange, null));
    }

    @Override
    public void doLock(EntityLongRangeEdit edit, EntityLongRange entityLongRange) {
        var entityLongRangeDescription = coreControl.getEntityLongRangeDescription(entityLongRange, getPreferredLanguage());
        var entityLongRangeDetail = entityLongRange.getLastDetail();
        var minimumLongValue = entityLongRangeDetail.getMinimumLongValue();
        var maximumLongValue = entityLongRangeDetail.getMaximumLongValue();

        edit.setEntityLongRangeName(entityLongRangeDetail.getEntityLongRangeName());
        edit.setMinimumLongValue(minimumLongValue == null ? null : minimumLongValue.toString());
        edit.setMaximumLongValue(maximumLongValue == null ? null : maximumLongValue.toString());
        edit.setIsDefault(entityLongRangeDetail.getIsDefault().toString());
        edit.setSortOrder(entityLongRangeDetail.getSortOrder().toString());

        if(entityLongRangeDescription != null) {
            edit.setDescription(entityLongRangeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityLongRange entityLongRange) {
        var strMinimumLongValue = edit.getMinimumLongValue();
        var minimumLongValue = strMinimumLongValue == null ? null : Long.valueOf(strMinimumLongValue);
        var strMaximumLongValue = edit.getMaximumLongValue();
        var maximumLongValue = strMaximumLongValue == null ? null : Long.valueOf(strMaximumLongValue);

        if(minimumLongValue == null || maximumLongValue == null || maximumLongValue >= minimumLongValue) {
            var entityLongRangeName = edit.getEntityLongRangeName();
            var duplicateEntityLongRange = coreControl.getEntityLongRangeByName(entityAttribute, entityLongRangeName);

            if(duplicateEntityLongRange != null && !entityLongRange.equals(duplicateEntityLongRange)) {
                addExecutionError(ExecutionErrors.DuplicateEntityLongRangeName.name(), entityLongRangeName);
            }
        } else {
            addExecutionError(ExecutionErrors.MinimumValueGreaterThanMaximumValue.name(), strMinimumLongValue, strMaximumLongValue);
        }
    }

    @Override
    public void doUpdate(EntityLongRange entityLongRange) {
        var partyPK = getPartyPK();
        var entityLongRangeDetailValue = coreControl.getEntityLongRangeDetailValueForUpdate(entityLongRange);
        var entityLongRangeDescription = coreControl.getEntityLongRangeDescriptionForUpdate(entityLongRange, getPreferredLanguage());
        var strMinimumLongValue = edit.getMinimumLongValue();
        var strMaximumLongValue = edit.getMaximumLongValue();
        var description = edit.getDescription();

        entityLongRangeDetailValue.setEntityLongRangeName(edit.getEntityLongRangeName());
        entityLongRangeDetailValue.setMinimumLongValue(strMinimumLongValue == null ? null : Long.valueOf(strMinimumLongValue));
        entityLongRangeDetailValue.setMaximumLongValue(strMaximumLongValue == null ? null : Long.valueOf(strMaximumLongValue));
        entityLongRangeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityLongRangeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateEntityLongRangeFromValue(entityLongRangeDetailValue, partyPK);

        if(entityLongRangeDescription == null && description != null) {
            coreControl.createEntityLongRangeDescription(entityLongRange, getPreferredLanguage(), description, partyPK);
        } else {
            if(entityLongRangeDescription != null && description == null) {
                coreControl.deleteEntityLongRangeDescription(entityLongRangeDescription, partyPK);
            } else {
                if(entityLongRangeDescription != null && description != null) {
                    var entityLongRangeDescriptionValue = coreControl.getEntityLongRangeDescriptionValue(entityLongRangeDescription);

                    entityLongRangeDescriptionValue.setDescription(description);
                    coreControl.updateEntityLongRangeDescriptionFromValue(entityLongRangeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
