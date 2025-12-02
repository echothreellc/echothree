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

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.RelatedItemEdit;
import com.echothree.control.user.item.common.form.EditRelatedItemForm;
import com.echothree.control.user.item.common.result.EditRelatedItemResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.RelatedItemSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.RelatedItem;
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
public class EditRelatedItemCommand
        extends BaseAbstractEditCommand<RelatedItemSpec, RelatedItemEdit, EditRelatedItemResult, RelatedItem, Item> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.RelatedItem.name(), SecurityRoles.Edit.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditRelatedItemCommand */
    public EditRelatedItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditRelatedItemResult getResult() {
        return ItemResultFactory.getEditRelatedItemResult();
    }
    
    @Override
    public RelatedItemEdit getEdit() {
        return ItemEditFactory.getRelatedItemEdit();
    }
    
    @Override
    public RelatedItem getEntity(EditRelatedItemResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        RelatedItem relatedItem = null;
        var relatedItemTypeName = spec.getRelatedItemTypeName();
        var relatedItemType = itemControl.getRelatedItemTypeByName(relatedItemTypeName);

        if(relatedItemType != null) {
            var fromItemName = spec.getFromItemName();
            var fromItem = itemControl.getItemByName(fromItemName);

            if(fromItem != null) {
                var toItemName = spec.getToItemName();
                var toItem = itemControl.getItemByName(toItemName);

                if(toItem != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        relatedItem = itemControl.getRelatedItem(relatedItemType, fromItem, toItem);
                    } else { // EditMode.UPDATE
                        relatedItem = itemControl.getRelatedItemForUpdate(relatedItemType, fromItem, toItem);
                    }

                    if(relatedItem == null) {
                        addExecutionError(ExecutionErrors.UnknownRelatedItem.name(), relatedItemTypeName, fromItemName, toItemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToItemName.name(), toItemName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromItemName.name(), fromItemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownRelatedItemTypeName.name(), relatedItemTypeName);
        }

        return relatedItem;
    }
    
    @Override
    public Item getLockEntity(RelatedItem relatedItem) {
        return relatedItem.getLastDetail().getFromItem();
    }
    
    @Override
    public void fillInResult(EditRelatedItemResult result, RelatedItem relatedItem) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        result.setRelatedItem(itemControl.getRelatedItemTransfer(getUserVisit(), relatedItem));
    }
    
    @Override
    public void doLock(RelatedItemEdit edit, RelatedItem relatedItem) {
        edit.setSortOrder(relatedItem.getLastDetail().getSortOrder().toString());
    }

    @Override
    public void doUpdate(RelatedItem relatedItem) {
        var itemControl = Session.getModelController(ItemControl.class);
        var relatedItemValueDetail = itemControl.getRelatedItemDetailValueForUpdate(relatedItem);

        relatedItemValueDetail.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateRelatedItemFromValue(relatedItemValueDetail, getPartyPK());
    }
    
}
