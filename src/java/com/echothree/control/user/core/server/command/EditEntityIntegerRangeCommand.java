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
import com.echothree.control.user.core.common.edit.EntityIntegerRangeEdit;
import com.echothree.control.user.core.common.form.EditEntityIntegerRangeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityIntegerRangeResult;
import com.echothree.control.user.core.common.spec.EntityIntegerRangeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
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
public class EditEntityIntegerRangeCommand
        extends BaseAbstractEditCommand<EntityIntegerRangeSpec, EntityIntegerRangeEdit, EditEntityIntegerRangeResult, EntityIntegerRange, EntityIntegerRange> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityIntegerRange.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityIntegerRangeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityIntegerRangeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEntityIntegerRangeCommand */
    public EditEntityIntegerRangeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
   public EditEntityIntegerRangeResult getResult() {
        return CoreResultFactory.getEditEntityIntegerRangeResult();
    }

    @Override
    public EntityIntegerRangeEdit getEdit() {
        return CoreEditFactory.getEntityIntegerRangeEdit();
    }

    EntityAttribute entityAttribute = null;
    
    @Override
    public EntityIntegerRange getEntity(EditEntityIntegerRangeResult result) {
        EntityIntegerRange entityIntegerRange = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                var entityAttributeName = spec.getEntityAttributeName();
                
                entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    var entityIntegerRangeName = spec.getEntityIntegerRangeName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        entityIntegerRange = coreControl.getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName);
                    } else { // EditMode.UPDATE
                        entityIntegerRange = coreControl.getEntityIntegerRangeByNameForUpdate(entityAttribute, entityIntegerRangeName);
                    }

                    if(entityIntegerRange == null) {
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

        return entityIntegerRange;
    }

    @Override
    public EntityIntegerRange getLockEntity(EntityIntegerRange entityIntegerRange) {
        return entityIntegerRange;
    }

    @Override
    public void fillInResult(EditEntityIntegerRangeResult result, EntityIntegerRange entityIntegerRange) {

        result.setEntityIntegerRange(coreControl.getEntityIntegerRangeTransfer(getUserVisit(), entityIntegerRange, null));
    }

    @Override
    public void doLock(EntityIntegerRangeEdit edit, EntityIntegerRange entityIntegerRange) {
        var entityIntegerRangeDescription = coreControl.getEntityIntegerRangeDescription(entityIntegerRange, getPreferredLanguage());
        var entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
        var minimumIntegerValue = entityIntegerRangeDetail.getMinimumIntegerValue();
        var maximumIntegerValue = entityIntegerRangeDetail.getMaximumIntegerValue();

        edit.setEntityIntegerRangeName(entityIntegerRangeDetail.getEntityIntegerRangeName());
        edit.setMinimumIntegerValue(minimumIntegerValue == null ? null : minimumIntegerValue.toString());
        edit.setMaximumIntegerValue(maximumIntegerValue == null ? null : maximumIntegerValue.toString());
        edit.setIsDefault(entityIntegerRangeDetail.getIsDefault().toString());
        edit.setSortOrder(entityIntegerRangeDetail.getSortOrder().toString());

        if(entityIntegerRangeDescription != null) {
            edit.setDescription(entityIntegerRangeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityIntegerRange entityIntegerRange) {
        var strMinimumIntegerValue = edit.getMinimumIntegerValue();
        var minimumIntegerValue = strMinimumIntegerValue == null ? null : Integer.valueOf(strMinimumIntegerValue);
        var strMaximumIntegerValue = edit.getMaximumIntegerValue();
        var maximumIntegerValue = strMaximumIntegerValue == null ? null : Integer.valueOf(strMaximumIntegerValue);

        if(minimumIntegerValue == null || maximumIntegerValue == null || maximumIntegerValue >= minimumIntegerValue) {
            var entityIntegerRangeName = edit.getEntityIntegerRangeName();
            var duplicateEntityIntegerRange = coreControl.getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName);

            if(duplicateEntityIntegerRange != null && !entityIntegerRange.equals(duplicateEntityIntegerRange)) {
                addExecutionError(ExecutionErrors.DuplicateEntityIntegerRangeName.name(), entityIntegerRangeName);
            }
        } else {
            addExecutionError(ExecutionErrors.MinimumValueGreaterThanMaximumValue.name(), strMinimumIntegerValue, strMaximumIntegerValue);
        }
    }

    @Override
    public void doUpdate(EntityIntegerRange entityIntegerRange) {
        var partyPK = getPartyPK();
        var entityIntegerRangeDetailValue = coreControl.getEntityIntegerRangeDetailValueForUpdate(entityIntegerRange);
        var entityIntegerRangeDescription = coreControl.getEntityIntegerRangeDescriptionForUpdate(entityIntegerRange, getPreferredLanguage());
        var strMinimumIntegerValue = edit.getMinimumIntegerValue();
        var strMaximumIntegerValue = edit.getMaximumIntegerValue();
        var description = edit.getDescription();

        entityIntegerRangeDetailValue.setEntityIntegerRangeName(edit.getEntityIntegerRangeName());
        entityIntegerRangeDetailValue.setMinimumIntegerValue(strMinimumIntegerValue == null ? null : Integer.valueOf(strMinimumIntegerValue));
        entityIntegerRangeDetailValue.setMaximumIntegerValue(strMaximumIntegerValue == null ? null : Integer.valueOf(strMaximumIntegerValue));
        entityIntegerRangeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityIntegerRangeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, partyPK);

        if(entityIntegerRangeDescription == null && description != null) {
            coreControl.createEntityIntegerRangeDescription(entityIntegerRange, getPreferredLanguage(), description, partyPK);
        } else {
            if(entityIntegerRangeDescription != null && description == null) {
                coreControl.deleteEntityIntegerRangeDescription(entityIntegerRangeDescription, partyPK);
            } else {
                if(entityIntegerRangeDescription != null && description != null) {
                    var entityIntegerRangeDescriptionValue = coreControl.getEntityIntegerRangeDescriptionValue(entityIntegerRangeDescription);

                    entityIntegerRangeDescriptionValue.setDescription(description);
                    coreControl.updateEntityIntegerRangeDescriptionFromValue(entityIntegerRangeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
