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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemAliasTypeEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemAliasTypeForm;
import com.echothree.control.user.item.common.result.EditItemAliasTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemAliasTypeUniversalSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemAliasTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemAliasTypeCommand
        extends BaseAbstractEditCommand<ItemAliasTypeUniversalSpec, ItemAliasTypeEdit, EditItemAliasTypeResult, ItemAliasType, ItemAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("ItemAliasChecksumTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AllowMultiple", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditItemAliasTypeCommand */
    public EditItemAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemAliasTypeResult getResult() {
        return ItemResultFactory.getEditItemAliasTypeResult();
    }
    
    @Override
    public ItemAliasTypeEdit getEdit() {
        return ItemEditFactory.getItemAliasTypeEdit();
    }
    
    @Override
    public ItemAliasType getEntity(EditItemAliasTypeResult result) {
        return ItemAliasTypeLogic.getInstance().getItemAliasTypeByUniversalSpec(this,
                spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public ItemAliasType getLockEntity(ItemAliasType itemAliasType) {
        return itemAliasType;
    }
    
    @Override
    public void fillInResult(EditItemAliasTypeResult result, ItemAliasType itemAliasType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        
        result.setItemAliasType(itemControl.getItemAliasTypeTransfer(getUserVisit(), itemAliasType));
    }
    
    ItemAliasChecksumType itemAliasChecksumType;
    
    @Override
    public void doLock(ItemAliasTypeEdit edit, ItemAliasType itemAliasType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var itemAliasTypeDescription = itemControl.getItemAliasTypeDescription(itemAliasType, getPreferredLanguage());
        final var itemAliasTypeDetail = itemAliasType.getLastDetail();
        
        edit.setItemAliasTypeName(itemAliasTypeDetail.getItemAliasTypeName());
        edit.setValidationPattern(itemAliasTypeDetail.getValidationPattern());
        edit.setItemAliasChecksumTypeName(itemAliasTypeDetail.getItemAliasChecksumType().getItemAliasChecksumTypeName());
        edit.setAllowMultiple(itemAliasTypeDetail.getAllowMultiple().toString());
        edit.setIsDefault(itemAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(itemAliasTypeDetail.getSortOrder().toString());

        if(itemAliasTypeDescription != null) {
            edit.setDescription(itemAliasTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(ItemAliasType itemAliasType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var itemAliasTypeName = edit.getItemAliasTypeName();
        final var duplicateItemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName);

        if(duplicateItemAliasType == null || itemAliasType.equals(duplicateItemAliasType)) {
            final var itemAliasChecksumTypeName = edit.getItemAliasChecksumTypeName();

            itemAliasChecksumType = itemControl.getItemAliasChecksumTypeByName(itemAliasChecksumTypeName);

            if(itemAliasChecksumType == null) {
                addExecutionError(ExecutionErrors.UnknownItemAliasChecksumTypeName.name(), itemAliasChecksumTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemAliasTypeName.name(), itemAliasTypeName);
        }
    }
    
    @Override
    public void doUpdate(ItemAliasType itemAliasType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var partyPK = getPartyPK();
        final var itemAliasTypeDetailValue = itemControl.getItemAliasTypeDetailValueForUpdate(itemAliasType);
        final var itemAliasTypeDescription = itemControl.getItemAliasTypeDescriptionForUpdate(itemAliasType, getPreferredLanguage());
        final var description = edit.getDescription();

        itemAliasTypeDetailValue.setItemAliasTypeName(edit.getItemAliasTypeName());
        itemAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        itemAliasTypeDetailValue.setItemAliasChecksumTypePK(itemAliasChecksumType.getPrimaryKey());
        itemAliasTypeDetailValue.setAllowMultiple(Boolean.valueOf(edit.getAllowMultiple()));
        itemAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ItemAliasTypeLogic.getInstance().updateItemAliasTypeFromValue(session, itemAliasTypeDetailValue, partyPK);

        if(itemAliasTypeDescription == null && description != null) {
            itemControl.createItemAliasTypeDescription(itemAliasType, getPreferredLanguage(), description, partyPK);
        } else if(itemAliasTypeDescription != null && description == null) {
            itemControl.deleteItemAliasTypeDescription(itemAliasTypeDescription, partyPK);
        } else if(itemAliasTypeDescription != null && description != null) {
            var itemAliasTypeDescriptionValue = itemControl.getItemAliasTypeDescriptionValue(itemAliasTypeDescription);

            itemAliasTypeDescriptionValue.setDescription(description);
            itemControl.updateItemAliasTypeDescriptionFromValue(itemAliasTypeDescriptionValue, partyPK);
        }
    }
    
}
