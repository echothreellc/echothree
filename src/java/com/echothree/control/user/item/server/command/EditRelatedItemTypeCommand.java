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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.RelatedItemTypeEdit;
import com.echothree.control.user.item.common.form.EditRelatedItemTypeForm;
import com.echothree.control.user.item.common.result.EditRelatedItemTypeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.RelatedItemTypeUniversalSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.RelatedItemTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.RelatedItemType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditRelatedItemTypeCommand
        extends BaseAbstractEditCommand<RelatedItemTypeUniversalSpec, RelatedItemTypeEdit, EditRelatedItemTypeResult, RelatedItemType, RelatedItemType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.RelatedItemType.name(), SecurityRoles.Edit.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }

    /** Creates a new instance of EditRelatedItemTypeCommand */
    public EditRelatedItemTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRelatedItemTypeResult getResult() {
        return ItemResultFactory.getEditRelatedItemTypeResult();
    }

    @Override
    public RelatedItemTypeEdit getEdit() {
        return ItemEditFactory.getRelatedItemTypeEdit();
    }

    @Override
    public RelatedItemType getEntity(EditRelatedItemTypeResult result) {
        return RelatedItemTypeLogic.getInstance().getRelatedItemTypeByUniversalSpec(this,
                spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public RelatedItemType getLockEntity(RelatedItemType relatedItemType) {
        return relatedItemType;
    }

    @Override
    public void fillInResult(EditRelatedItemTypeResult result, RelatedItemType relatedItemType) {
        final var itemControl = Session.getModelController(ItemControl.class);

        result.setRelatedItemType(itemControl.getRelatedItemTypeTransfer(getUserVisit(), relatedItemType));
    }

    @Override
    public void doLock(RelatedItemTypeEdit edit, RelatedItemType relatedItemType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var relatedItemTypeDescription = itemControl.getRelatedItemTypeDescription(relatedItemType, getPreferredLanguage());
        final var relatedItemTypeDetail = relatedItemType.getLastDetail();

        edit.setRelatedItemTypeName(relatedItemTypeDetail.getRelatedItemTypeName());
        edit.setIsDefault(relatedItemTypeDetail.getIsDefault().toString());
        edit.setSortOrder(relatedItemTypeDetail.getSortOrder().toString());

        if(relatedItemTypeDescription != null) {
            edit.setDescription(relatedItemTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(RelatedItemType relatedItemType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var relatedItemTypeName = edit.getRelatedItemTypeName();
        final var duplicateRelatedItemType = itemControl.getRelatedItemTypeByName(relatedItemTypeName);

        if(duplicateRelatedItemType != null && !relatedItemType.equals(duplicateRelatedItemType)) {
            addExecutionError(ExecutionErrors.DuplicateRelatedItemTypeName.name(), relatedItemTypeName);
        }
    }

    @Override
    public void doUpdate(RelatedItemType relatedItemType) {
        final var itemControl = Session.getModelController(ItemControl.class);
        final var partyPK = getPartyPK();
        final var relatedItemTypeDetailValue = itemControl.getRelatedItemTypeDetailValueForUpdate(relatedItemType);
        final var relatedItemTypeDescription = itemControl.getRelatedItemTypeDescriptionForUpdate(relatedItemType, getPreferredLanguage());
        final var description = edit.getDescription();

        relatedItemTypeDetailValue.setRelatedItemTypeName(edit.getRelatedItemTypeName());
        relatedItemTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        relatedItemTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        RelatedItemTypeLogic.getInstance().updateRelatedItemTypeFromValue(relatedItemTypeDetailValue, partyPK);

        if(relatedItemTypeDescription == null && description != null) {
            itemControl.createRelatedItemTypeDescription(relatedItemType, getPreferredLanguage(), description, partyPK);
        } else if(relatedItemTypeDescription != null && description == null) {
            itemControl.deleteRelatedItemTypeDescription(relatedItemTypeDescription, partyPK);
        } else if(relatedItemTypeDescription != null && description != null) {
            var relatedItemTypeDescriptionValue = itemControl.getRelatedItemTypeDescriptionValue(relatedItemTypeDescription);

            relatedItemTypeDescriptionValue.setDescription(description);
            itemControl.updateRelatedItemTypeDescriptionFromValue(relatedItemTypeDescriptionValue, partyPK);
        }
    }

}
