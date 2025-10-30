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

import com.echothree.control.user.item.common.form.GetItemDescriptionsForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.factory.ItemDescriptionFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetItemDescriptionsCommand
        extends BaseMultipleEntitiesCommand<ItemDescription, GetItemDescriptionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescription.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetItemDescriptionsCommand */
    public GetItemDescriptionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Item item;

    @Override
    protected Collection<ItemDescription> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);
        Collection<ItemDescription> entities = null;
        var itemName = form.getItemName();

        item = ItemLogic.getInstance().getItemByName(this, itemName);

        if(!hasExecutionErrors()) {
            var itemDescriptionTypeUseTypeName = form.getItemDescriptionTypeUseTypeName();
            var itemDescriptionTypeUseType = itemDescriptionTypeUseTypeName == null ? null : itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

            if(itemDescriptionTypeUseTypeName == null || itemDescriptionTypeUseType != null) {
                if(itemDescriptionTypeUseType == null) {
                    if(form.getLanguageIsoName() == null) {
                        entities = itemControl.getItemDescriptionsByItem(item);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidParameterCombination.name());
                    }
                } else {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = form.getLanguageIsoName();
                    var language = languageIsoName == null ? getPreferredLanguage() : partyControl.getLanguageByIsoName(languageIsoName);

                    if(languageIsoName == null || language != null) {
                        var itemDescriptionTypeUses = itemControl.getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);

                        entities = new ArrayList<>();

                        for(var itemDescriptionTypeUse : itemDescriptionTypeUses) {
                            var itemDescription = itemControl.getBestItemDescription(itemDescriptionTypeUse.getItemDescriptionType(), item, language);

                            if(itemDescription != null) {
                                entities.add(itemDescription);
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ItemDescription> entities) {
        var result = ItemResultFactory.getGetItemDescriptionsResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ItemDescriptionFactory.class)) {
                result.setItemDescriptionCount(itemControl.countItemDescriptionsByItem(item));
            }

            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setItemDescriptions(itemControl.getItemDescriptionTransfers(userVisit, entities));
        }

        return result;
    }

}
