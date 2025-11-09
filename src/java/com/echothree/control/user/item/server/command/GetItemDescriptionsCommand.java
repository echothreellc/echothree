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
import com.echothree.model.control.item.server.logic.ItemDescriptionTypeUseTypeLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.factory.ItemDescriptionFactory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GetItemDescriptionsCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemDescription, GetItemDescriptionsForm> {

    @Inject
    ItemControl itemControl;

    @Inject
    PartyControl partyControl;

    @Inject
    ItemLogic itemLogic;

    @Inject
    ItemDescriptionTypeUseTypeLogic itemDescriptionTypeUseTypeLogic;

    @Inject
    LanguageLogic languageLogic;

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
    ItemDescriptionTypeUseType itemDescriptionTypeUseType;
    Language language;

    Collection<ItemDescription> entities;

    @Override
    protected void handleForm() {
        var itemName = form.getItemName();

        item = itemLogic.getItemByName(this, itemName);

        // If an Item was found...
        if(!hasExecutionErrors()) {
            var itemDescriptionTypeUseTypeName = form.getItemDescriptionTypeUseTypeName();

            itemDescriptionTypeUseType = itemDescriptionTypeUseTypeName == null ? null : itemDescriptionTypeUseTypeLogic.getItemDescriptionTypeUseTypeByName(this, itemDescriptionTypeUseTypeName);

            if(!hasExecutionErrors()) {
                var languageIsoName = form.getLanguageIsoName();

                if(itemDescriptionTypeUseType == null) {
                    // If no ItemDescriptionTypeUseTypeName was specified, a LanguageIsoName must not be specified either...
                    if(languageIsoName != null) {
                        addExecutionError(ExecutionErrors.InvalidParameterCombination.name());
                    }
                } else {
                    // If an ItemDescriptionTypeUseTypeName was specified, a Language must either be specific or pulled from the default...
                    language = languageIsoName == null ? getPreferredLanguage() : languageLogic.getLanguageByName(this, languageIsoName);
                }
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(itemDescriptionTypeUseType == null) {
                totalEntities = itemControl.countItemDescriptionsByItem(item);
            } else {
                // Get all ItemDescriptionTypeUses for the given ItemDescriptionTypeUseType...
                var itemDescriptionTypeUses = itemControl.getItemDescriptionTypeUsesByItemDescriptionTypeUseType(itemDescriptionTypeUseType);

                entities = new ArrayList<>();

                // And now try to get the best possible Item Description Type for each ItemDescriptionTypeUse...
                for(var itemDescriptionTypeUse : itemDescriptionTypeUses) {
                    // There are further comments in ItemControl on the algorithm for this.
                    var itemDescription = itemControl.getBestItemDescription(itemDescriptionTypeUse.getItemDescriptionType(), item, language);

                    if(itemDescription != null) {
                        entities.add(itemDescription);
                    }
                }

                totalEntities = (long)entities.size();
            }
        }


        return totalEntities;
    }

    @Override
    protected Collection<ItemDescription> getEntities() {
        // If an Item was found...
        if(!hasExecutionErrors()) {
            if(itemDescriptionTypeUseType == null) {
                // If no ItemDescriptionTypeUseTypeName was specified, a LanguageIsoName must not be specified either...
                if(language == null) {
                    // Get all ItemDescriptions for the Item regardless of Language...
                    entities = itemControl.getItemDescriptionsByItem(item);
                }
            } else {
                getTotalEntities(); // Populates entities in this case.
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ItemDescription> entities) {
        var result = ItemResultFactory.getGetItemDescriptionsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setItem(itemControl.getItemTransfer(userVisit, item));

            if(itemDescriptionTypeUseType != null) {
                result.setItemDescriptionTypeUseType(itemControl.getItemDescriptionTypeUseTypeTransfer(userVisit, itemDescriptionTypeUseType));
            }

            if(language != null) {
                result.setLanguage(partyControl.getLanguageTransfer(userVisit, language));
            }

            if(session.hasLimit(ItemDescriptionFactory.class)) {
                result.setItemDescriptionCount(getTotalEntities());
            }

            result.setItemDescriptions(itemControl.getItemDescriptionTransfers(userVisit, entities));
        }

        return result;
    }

}
