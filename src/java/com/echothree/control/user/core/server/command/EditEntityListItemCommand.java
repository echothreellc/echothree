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
import com.echothree.control.user.core.common.edit.EntityListItemEdit;
import com.echothree.control.user.core.common.form.EditEntityListItemForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityListItemResult;
import com.echothree.control.user.core.common.spec.EntityListItemSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityListItemDescriptionValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
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

public class EditEntityListItemCommand
        extends BaseAbstractEditCommand<EntityListItemSpec, EntityListItemEdit, EditEntityListItemResult, EntityListItem, EntityListItem> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityListItem.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEntityListItemCommand */
    public EditEntityListItemCommand(UserVisitPK userVisitPK, EditEntityListItemForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
   public EditEntityListItemResult getResult() {
        return CoreResultFactory.getEditEntityListItemResult();
    }

    @Override
    public EntityListItemEdit getEdit() {
        return CoreEditFactory.getEntityListItemEdit();
    }

    EntityAttribute entityAttribute = null;
    
    @Override
    public EntityListItem getEntity(EditEntityListItemResult result) {
        var coreControl = getCoreControl();
        EntityListItem entityListItem = null;
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                String entityAttributeName = spec.getEntityAttributeName();
                
                entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    String entityListItemName = spec.getEntityListItemName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        entityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName);
                    } else { // EditMode.UPDATE
                        entityListItem = coreControl.getEntityListItemByNameForUpdate(entityAttribute, entityListItemName);
                    }

                    if(entityListItem == null) {
                        addExecutionError(ExecutionErrors.UnknownEntityListItemName.name(), componentVendorName, entityTypeName, entityAttributeName, entityListItemName);
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

        return entityListItem;
    }

    @Override
    public EntityListItem getLockEntity(EntityListItem entityListItem) {
        return entityListItem;
    }

    @Override
    public void fillInResult(EditEntityListItemResult result, EntityListItem entityListItem) {
        var coreControl = getCoreControl();

        result.setEntityListItem(coreControl.getEntityListItemTransfer(getUserVisit(), entityListItem, null));
    }

    @Override
    public void doLock(EntityListItemEdit edit, EntityListItem entityListItem) {
        var coreControl = getCoreControl();
        EntityListItemDescription entityListItemDescription = coreControl.getEntityListItemDescription(entityListItem, getPreferredLanguage());
        EntityListItemDetail entityListItemDetail = entityListItem.getLastDetail();

        edit.setEntityListItemName(entityListItemDetail.getEntityListItemName());
        edit.setIsDefault(entityListItemDetail.getIsDefault().toString());
        edit.setSortOrder(entityListItemDetail.getSortOrder().toString());

        if(entityListItemDescription != null) {
            edit.setDescription(entityListItemDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityListItem entityListItem) {
        var coreControl = getCoreControl();
        String entityListItemName = edit.getEntityListItemName();
        EntityListItem duplicateEntityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName);

        if(duplicateEntityListItem != null && !entityListItem.equals(duplicateEntityListItem)) {
            addExecutionError(ExecutionErrors.DuplicateEntityListItemName.name(), entityListItemName);
        }
    }

    @Override
    public void doUpdate(EntityListItem entityListItem) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        EntityListItemDetailValue entityListItemDetailValue = coreControl.getEntityListItemDetailValueForUpdate(entityListItem);
        EntityListItemDescription entityListItemDescription = coreControl.getEntityListItemDescriptionForUpdate(entityListItem, getPreferredLanguage());
        String description = edit.getDescription();

        entityListItemDetailValue.setEntityListItemName(edit.getEntityListItemName());
        entityListItemDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityListItemDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        EntityAttributeLogic.getInstance().updateEntityListItemFromValue(session, entityListItemDetailValue, partyPK);

        if(entityListItemDescription == null && description != null) {
            coreControl.createEntityListItemDescription(entityListItem, getPreferredLanguage(), description, partyPK);
        } else {
            if(entityListItemDescription != null && description == null) {
                coreControl.deleteEntityListItemDescription(entityListItemDescription, partyPK);
            } else {
                if(entityListItemDescription != null && description != null) {
                    EntityListItemDescriptionValue entityListItemDescriptionValue = coreControl.getEntityListItemDescriptionValue(entityListItemDescription);

                    entityListItemDescriptionValue.setDescription(description);
                    coreControl.updateEntityListItemDescriptionFromValue(entityListItemDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
