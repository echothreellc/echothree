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

import com.echothree.control.user.item.common.form.CreateRelatedItemForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateRelatedItemCommand
        extends BaseSimpleCommand<CreateRelatedItemForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.RelatedItem.name(), SecurityRoles.Create.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RelatedItemTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("FromItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ToItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateRelatedItemCommand */
    public CreateRelatedItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ItemResultFactory.getCreateRelatedItemResult();
        var itemControl = Session.getModelController(ItemControl.class);
        var relatedItemTypeName = form.getRelatedItemTypeName();
        var relatedItemType = itemControl.getRelatedItemTypeByName(relatedItemTypeName);
        
        if(relatedItemType != null) {
            var fromItemName = form.getFromItemName();
            var fromItem = itemControl.getItemByName(fromItemName);
            
            if(fromItem != null) {
                var toItemName = form.getToItemName();
                var toItem = itemControl.getItemByName(toItemName);
                
                if(toItem != null) {
                    if(!fromItem.equals(toItem)) {
                        var relatedItem = itemControl.getRelatedItem(relatedItemType, fromItem, toItem);

                        if(relatedItem == null) {
                            var sortOrder = Integer.valueOf(form.getSortOrder());

                            itemControl.createRelatedItem(relatedItemType, fromItem, toItem, sortOrder, getPartyPK());

                            result.setRelatedItemTypeName(relatedItemType.getLastDetail().getRelatedItemTypeName());
                            result.setFromItemName(fromItem.getLastDetail().getItemName());
                            result.setToItemName(toItem.getLastDetail().getItemName());
                            result.setEntityRef(relatedItemType.getPrimaryKey().getEntityRef());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateRelatedItem.name(),
                                    relatedItemType.getLastDetail().getRelatedItemTypeName(),
                                    fromItem.getLastDetail().getItemName(), toItem.getLastDetail().getItemName());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.CannotRelateToSelf.name(),
                                fromItem.getLastDetail().getItemName(), toItem.getLastDetail().getItemName());
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

        return result;
    }
    
}
